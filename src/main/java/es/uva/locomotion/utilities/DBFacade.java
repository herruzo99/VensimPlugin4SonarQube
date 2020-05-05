package es.uva.locomotion.utilities;

import es.uva.locomotion.parser.Symbol;
import es.uva.locomotion.parser.SymbolTable;
import es.uva.locomotion.parser.SymbolType;
import es.uva.locomotion.utilities.exceptions.ConnectionFailedException;
import es.uva.locomotion.utilities.exceptions.EmptyServiceException;
import es.uva.locomotion.utilities.exceptions.InvalidServiceUrlException;
import es.uva.locomotion.utilities.exceptions.ServiceResponseFormatNotValid;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;


import javax.json.*;

import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

public class DBFacade {

    public static final String FIELD_SYMBOL_NAME = "name";
    public static final String FIELD_SYMBOLS = "symbols";
    public static final String FIELD_INDEXES = "indexes";
    public static final String FIELD_SYMBOL_COMMENT = "definition";
    public static final String FIELD_SYMBOL_CATEGORY = "category";
    public static final String FIELD_SYMBOL_TYPE = "programming symbol type";
    public static final String FIELD_SYMBOL_INDEXES = "indexes";
    public static final String FIELD_SYMBOL_MODULES = "modules";
    public static final String FIELD_INDEX_VALUES = "values";
    public static final String FIELD_INJECTION_IS_INDEXED = "is_indexed";
    public static final String FIELD_INJECTION_MODULE = "module";
    public static  final String FIELD_SYMBOL_UNITS = "unit";

    public static final List<String> REQUIRED_FIELDS_IN_SYMBOL = List.of(FIELD_SYMBOL_NAME,FIELD_SYMBOL_TYPE,FIELD_SYMBOL_COMMENT,FIELD_SYMBOL_CATEGORY,FIELD_SYMBOL_INDEXES,FIELD_SYMBOL_MODULES, FIELD_SYMBOL_UNITS);
    public static final List<String> REQUIRED_FIELDS_IN_INDEXES = List.of(FIELD_SYMBOL_NAME,FIELD_INDEX_VALUES, FIELD_SYMBOL_COMMENT);
    private static final String FIELD_SYMBOL_MODULES_MAIN = "main";
    private static final String FIELD_SYMBOL_MODULES_SECONDARY = "secondary";
    protected static ServiceConnectionHandler handler = new ServiceConnectionHandler();

    protected static Logger LOG = Loggers.get(DBFacade.class.getSimpleName());


    /**
     * Searches for the symbols given as a parameter in the DB
     * @param symbols
     * @return A SymbolTable with the symbols that have been found in the DB (might not be all).
     * The symbols contain the information found in the DB (type, module, etc).
     *
     * If the response from the service contains a duplicated symbol, it takes the first one and logs a warning.
     *
     * @throws ServiceResponseFormatNotValid If the response of the service doesn't have a valid format.
     * @throws InvalidServiceUrlException If the url isn't valid (doesn't have a protocol or invalid format)
     * @throws ConnectionFailedException If the domain address can't be resolved or the page is inaccessible.
     * @throws EmptyServiceException If {@code serviceUrl} is empty if null
     * @throws IllegalArgumentException If {@code symbols} is null
     */
    public static SymbolTable getExistingSymbolsFromDB(String serviceUrl, List<String> symbols) {

        String serviceResponse = handler.sendRequestToDictionaryService(serviceUrl, symbols);

        JsonReader jsonReader = Json.createReader(new StringReader(serviceResponse));
        try {
            JsonObject symbolsFound = jsonReader.readObject();

            return createSymbolTableFromJson(symbolsFound);
        } catch (JsonException ex) {
            ex.printStackTrace(); //TODO Mejorar el mensaje que dice el throw, porque esta bien por si manda un array en vez de un object,
                                  // pero no si el object no es valido (porque falta una coma por ejemplo)
            throw new ServiceResponseFormatNotValid("Expected an object.",serviceResponse);
        }catch (ServiceResponseFormatNotValid ex){
            ex.setServiceResponse(serviceResponse);
            throw ex;
        }
        finally {
            jsonReader.close();
        }
    }

    protected static SymbolTable createSymbolTableFromJson(JsonObject symbolsFound) {
        SymbolTable table = new SymbolTable();

        if(!symbolsFound.containsKey(FIELD_SYMBOLS)){
            throw new ServiceResponseFormatNotValid("Missing '"+FIELD_SYMBOLS+"' field.");
        }

        if(!symbolsFound.containsKey(FIELD_INDEXES)){
            throw new ServiceResponseFormatNotValid("Missing '"+FIELD_INDEXES+"' field.");
        }

        JsonArray symbols = symbolsFound.getJsonArray(FIELD_SYMBOLS);
        JsonArray indexes = symbolsFound.getJsonArray(FIELD_INDEXES);

        loadSymbols(table,symbols);
        loadIndexes(table,indexes);


        return table;
    }

    private static void loadIndexes(SymbolTable table, JsonArray indexes) {
        for(int i=0;i<indexes.size();i++) {

            JsonObject jsonIndex = indexes.getJsonObject(i);
            validateJsonIndex(jsonIndex);

            String name = jsonIndex.getString(FIELD_SYMBOL_NAME);
            Symbol index = UtilityFunctions.getSymbolOrCreate(table,name);

            String comment = jsonIndex.getString(FIELD_SYMBOL_COMMENT);
            index.setComment(comment);
            index.setType(SymbolType.Subscript);


            JsonArray jsonValues= jsonIndex.getJsonArray(FIELD_INDEX_VALUES);
            for(int v=0;v<jsonValues.size();v++) {
                String indexValue = jsonValues.getString(v);
                Symbol valueSymbol = UtilityFunctions.getSymbolOrCreate(table,indexValue);
                valueSymbol.setType(SymbolType.Subscript_Value);
                index.addDependency(valueSymbol);
            }

        }
    }

    private static void validateJsonIndex(JsonObject jsonIndex){
        if(! jsonIndex.containsKey(FIELD_SYMBOL_NAME)){
            throw new ServiceResponseFormatNotValid("Missing '"+FIELD_SYMBOL_NAME+"' field from an index.");
        }

        String name = jsonIndex.getString(FIELD_SYMBOL_NAME);
        for(String field: REQUIRED_FIELDS_IN_INDEXES){
            if(!jsonIndex.containsKey(field)){
                throw new ServiceResponseFormatNotValid("Missing '"+field+"' field in the index '"+name+"'.");
            }
        }
    }

    private static void validateJsonSymbol(JsonObject jsonSymbol){
        if(! jsonSymbol.containsKey(FIELD_SYMBOL_NAME)){
            throw new ServiceResponseFormatNotValid("Missing '"+FIELD_SYMBOL_NAME+"' field from a symbol.");
        }

        String name = jsonSymbol.getString(FIELD_SYMBOL_NAME);
        for(String field: REQUIRED_FIELDS_IN_SYMBOL){
            if(! jsonSymbol.containsKey(field)){
                throw new ServiceResponseFormatNotValid("Missing '"+field+"' field in symbol '"+name+"'.");
            }
        }
    }
    private static void loadSymbols(SymbolTable table, JsonArray symbols) {
        for(int s=0;s<symbols.size();s++) {

            JsonObject jsonSymbol = symbols.getJsonObject(s);
            validateJsonSymbol(jsonSymbol);

            String name = jsonSymbol.getString(FIELD_SYMBOL_NAME);

            if(table.hasSymbol(name)){
                LOG.warn("Received duplicated symbol '" +name+"' from the dictionary service.");
                continue;
            }

            Symbol symbol = new Symbol(name);

            String comment = jsonSymbol.getString(FIELD_SYMBOL_COMMENT);
            symbol.setComment(comment);

            String units = jsonSymbol.getString(FIELD_SYMBOL_UNITS);
            symbol.setUnits(units);

            String category = jsonSymbol.getString(FIELD_SYMBOL_CATEGORY);
            symbol.setCategory(category);

            String type = jsonSymbol.getString(FIELD_SYMBOL_TYPE);
            try {
                symbol.setType(SymbolType.valueOf(type));
            }catch (IllegalArgumentException ex){
                throw new ServiceResponseFormatNotValid("The symbol '"+ name +"' has an unknown programming type: '"+ type +"'");
            }
            
            JsonArray jsonIndexes = jsonSymbol.getJsonArray(FIELD_SYMBOL_INDEXES);
            List<Symbol> indexes = new ArrayList<>();
            for(int i=0;i<jsonIndexes.size();i++) {
               String index = jsonIndexes.getString(i);
                indexes.add(UtilityFunctions.getSymbolOrCreate(table,index));
            }

            symbol.addIndexLine(indexes);

            JsonObject modules = jsonSymbol.getJsonObject(FIELD_SYMBOL_MODULES);
            symbol.addModule(modules.getString(FIELD_SYMBOL_MODULES_MAIN));

            JsonArray secondaryModules = modules.getJsonArray(FIELD_SYMBOL_MODULES_SECONDARY);
            for(int i=0;i<secondaryModules.size();i++) {
                String module = secondaryModules.getString(i);
                symbol.addModule(module);
            }
            table.addSymbol(symbol);

        }
    }


    public static void injectSymbols(String serviceUrl, String module, List<Symbol> symbols) {
        List<Symbol> rawSymbols = symbols.stream().filter(symbol -> !List.of(SymbolType.Subscript_Value, SymbolType.Subscript,
                SymbolType.UNDETERMINED, SymbolType.UNDETERMINED_FUNCTION, SymbolType.Function).contains(symbol.getType())).collect(Collectors.toList());

        List<Symbol> indexes = symbols.stream().filter(symbol -> symbol.getType()==SymbolType.Subscript).collect(Collectors.toList());

        rawSymbols.sort(Comparator.comparing(Symbol::getToken));
        indexes.sort(Comparator.comparing(Symbol::getToken));

        JsonArray jsonSymbols = getInjectSymbolsJson(rawSymbols);
        JsonArray jsonIndexes = getInjectIndexesJson(indexes);

        JsonObjectBuilder requestBuilder = Json.createObjectBuilder();

        requestBuilder.add(FIELD_SYMBOLS, jsonSymbols);
        requestBuilder.add(FIELD_INDEXES, jsonIndexes);
        requestBuilder.add(FIELD_INJECTION_MODULE,module.trim());

        handler.injectSymbols(serviceUrl,requestBuilder.build());
    }

    private static JsonArray getInjectSymbolsJson(List<Symbol> symbols){
        JsonArrayBuilder jsonSymbols = Json.createArrayBuilder();

        for(Symbol s:symbols){
            JsonObjectBuilder jsonSymbol = Json.createObjectBuilder();

            jsonSymbol.add(FIELD_SYMBOL_NAME, s.getToken().trim());
            jsonSymbol.add(FIELD_SYMBOL_UNITS, s.getUnits().trim());
            jsonSymbol.add(FIELD_SYMBOL_COMMENT, s.getComment().trim());
            jsonSymbol.add(FIELD_INJECTION_IS_INDEXED,!s.getIndexes().isEmpty());
            jsonSymbol.add(FIELD_SYMBOL_CATEGORY, s.getCategory().trim());
            jsonSymbol.add(FIELD_SYMBOL_TYPE, s.getType().toString());

            jsonSymbols.add(jsonSymbol);
        }

        return jsonSymbols.build();
    }

    private static JsonArray getInjectIndexesJson(List<Symbol> indexes){
        JsonArrayBuilder jsonIndexes = Json.createArrayBuilder();

        for(Symbol index:indexes){
            JsonObjectBuilder jsonSymbol = Json.createObjectBuilder();

            jsonSymbol.add(FIELD_SYMBOL_NAME, index.getToken().trim());

            JsonArrayBuilder jsonValues = Json.createArrayBuilder();
            List<Symbol> dependencies = new ArrayList<>(index.getDependencies());
            dependencies.sort(Comparator.comparing(Symbol::getToken));
            for(Symbol value:dependencies){
                jsonValues.add(value.getToken().trim());
            }
            jsonSymbol.add(FIELD_INDEX_VALUES, jsonValues.build());

            jsonIndexes.add(jsonSymbol);
        }

        return jsonIndexes.build();
    }
}
