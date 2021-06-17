package es.uva.locomotion.utilities;

import es.uva.locomotion.model.DataBaseRepresentation;
import es.uva.locomotion.model.Module;
import es.uva.locomotion.model.ViewTable;
import es.uva.locomotion.model.category.Category;
import es.uva.locomotion.model.symbol.ExcelRef;
import es.uva.locomotion.model.symbol.Symbol;
import es.uva.locomotion.model.symbol.SymbolTable;
import es.uva.locomotion.model.symbol.SymbolType;
import es.uva.locomotion.utilities.logs.VensimLogger;
import org.antlr.v4.runtime.misc.Triple;

import javax.json.*;
import java.util.*;
import java.util.stream.Collectors;

public class JsonDictoinaryDiffBuilder {


    public static final String MISSMATCHES = "missmatches";
    public static final String NOT_FOUND_IN_DB = "not_found_in_DB";
    public static final String NOT_FOUND_IN_LOCAL = "not_found_in_local";
    private final JsonArrayBuilder fileBuilder;

    public static final String KEY_COMMENT = "comment";
    public static final String KEY_UNITS = "units";
    public static final String KEY_DEPENDENCIES = "dependencies";
    public static final String KEY_LINES = "lines";
    public static final String KEY_TYPE = "type";
    public static final String KEY_PRIMARY_VIEW = "primary";
    public static final String KEY_SHADOW_VIEWS = "shadows";
    public static final String KEY_MODULE = "module";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_SUBCATEGORY = "subcategory";
    public static final String KEY_MODULES = "modules";
    public static final String KEY_CATEGORIES = "categories";
    public static final String KEY_NAME = "name";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_SUPER = "super";
    public static final String KEY_GROUP = "group";
    public static final String KEY_LOCAL = "Local";
    public static final String KEY_DICTIONARY = "Dictionary";
    public static final String KEY_INDEXES = "indexes";
    public static final String KEY_EXCEL = "excels";
    public static final String KEY_SHEET = "sheet";
    public static final String KEY_CELLRANGE = "cellrange";
    public static final String KEY_SERIES = "series";
    public static final String KEY_INFO = "info";
    public static final String KEY_FILENAME = "filename";

    protected static final VensimLogger logger = VensimLogger.getInstance();


    public JsonDictoinaryDiffBuilder() {
        fileBuilder = Json.createArrayBuilder();
    }

    public void addTables(String file, SymbolTable table, ViewTable viewTable, DataBaseRepresentation dbData) {
        JsonObjectBuilder tableBuilder = Json.createObjectBuilder();

        tableBuilder.add("file", file);
        if (dbData.getDataBaseSymbolTable() != null) {
            tableBuilder.add("symbols", symbolTableDiffToJson(table, dbData.getDataBaseSymbolTable()));
            tableBuilder.add("indexes", indexesTableDiffToJson(table, dbData.getDataBaseSymbolTable()));
            tableBuilder.add("indexes_values", indexValuesTableDiffToJson(table, dbData.getDataBaseSymbolTable()));
        }
        if (dbData.getModules() != null) {
            tableBuilder.add(KEY_MODULES, modulesDiffToJson(viewTable.getModules(), dbData.getModules()));
        }
        if (dbData.getCategoriesMap() != null) {
            tableBuilder.add(KEY_CATEGORIES, categoriesDiffToJson(viewTable.getCategoriesAndSubcategories(), dbData.getCategoriesMap().getCategoriesAndSubcategories()));
        }
        fileBuilder.add(tableBuilder);
    }

    private JsonObject symbolTableDiffToJson(SymbolTable symbolTable, SymbolTable dbTable) {

        JsonObjectBuilder tableBuilder = Json.createObjectBuilder();
        JsonObjectBuilder missmatchBuilder = Json.createObjectBuilder();
        JsonArrayBuilder missingLocalBuilder = Json.createArrayBuilder();
        JsonArrayBuilder missingDBBuilder = Json.createArrayBuilder();
        List<SymbolType> ignoreTypes = Arrays.asList(SymbolType.FUNCTION, SymbolType.SUBSCRIPT, SymbolType.SUBSCRIPT_VALUE);
        List<Symbol> localSymbols = symbolTable.getSymbols().stream().filter(symbol -> !ignoreTypes.contains(symbol.getType())).sorted(Comparator.comparing(Symbol::getToken)).collect(Collectors.toList());

        for (Symbol symbol : localSymbols) {

            if (dbTable.hasSymbol(symbol.getToken())) {
                Symbol dbSymbol = dbTable.getSymbol(symbol.getToken());

                if (!symbol.dbEquals(dbSymbol)) {
                    missmatchBuilder.add(symbol.getToken(), symbolDiffToJson(symbol, dbSymbol));
                }
                dbTable.removeSymbol(symbol.getToken());
            } else {
                if(symbol.isValid())
                    missingDBBuilder.add(symbol.getToken());
            }
        }

        List<Symbol> dbSymbols = dbTable.getSymbols().stream().filter(symbol -> !ignoreTypes.contains(symbol.getType())).sorted(Comparator.comparing(Symbol::getToken)).collect(Collectors.toList());

        for (Symbol dbsymbol : dbSymbols) {
            missingLocalBuilder.add(dbsymbol.getToken());
        }
        tableBuilder.add(MISSMATCHES, missmatchBuilder);
        tableBuilder.add(NOT_FOUND_IN_DB, missingDBBuilder);
        tableBuilder.add(NOT_FOUND_IN_LOCAL, missingLocalBuilder);
        return tableBuilder.build();
    }

    private JsonObject indexValuesTableDiffToJson(SymbolTable symbolTable, SymbolTable dbTable) {
        JsonObjectBuilder tableBuilder = Json.createObjectBuilder();
        JsonObjectBuilder missmatchBuilder = Json.createObjectBuilder();
        JsonArrayBuilder missingLocalBuilder = Json.createArrayBuilder();
        JsonArrayBuilder missingDBBuilder = Json.createArrayBuilder();
        List<SymbolType> select = Collections.singletonList(SymbolType.SUBSCRIPT_VALUE);
        List<Symbol> localSymbols = symbolTable.getSymbols().stream().filter(symbol -> select.contains(symbol.getType())).sorted(Comparator.comparing(Symbol::getToken)).collect(Collectors.toList());


        for (Symbol symbol : localSymbols) {

            if (dbTable.hasSymbol(symbol.getToken())) {
                Symbol dbSymbol = dbTable.getSymbol(symbol.getToken());
                if (!symbol.dbEquals(dbSymbol)) {
                    missmatchBuilder.add(symbol.getToken(), symbolDiffToJson(symbol, dbSymbol));
                }
                dbTable.removeSymbol(symbol.getToken());
            } else {
                if(symbol.isValid())
                    missingDBBuilder.add(symbol.getToken());
            }
        }

        List<Symbol> dbSymbols = dbTable.getSymbols().stream().filter(symbol -> select.contains(symbol.getType())).sorted(Comparator.comparing(Symbol::getToken)).collect(Collectors.toList());

        for (Symbol dbsymbol : dbSymbols) {
            missingLocalBuilder.add(dbsymbol.getToken());
        }
        tableBuilder.add(MISSMATCHES, missmatchBuilder);
        tableBuilder.add(NOT_FOUND_IN_DB, missingDBBuilder);
        tableBuilder.add(NOT_FOUND_IN_LOCAL, missingLocalBuilder);
        return tableBuilder.build();
    }

    private JsonObject indexesTableDiffToJson(SymbolTable symbolTable, SymbolTable dbTable) {
        JsonObjectBuilder tableBuilder = Json.createObjectBuilder();
        JsonObjectBuilder missmatchBuilder = Json.createObjectBuilder();
        JsonArrayBuilder missingLocalBuilder = Json.createArrayBuilder();
        JsonArrayBuilder missingDBBuilder = Json.createArrayBuilder();
        List<SymbolType> select = Collections.singletonList(SymbolType.SUBSCRIPT);
        List<Symbol> localSymbols = symbolTable.getSymbols().stream().filter(symbol -> select.contains(symbol.getType())).sorted(Comparator.comparing(Symbol::getToken)).collect(Collectors.toList());


        for (Symbol symbol : localSymbols) {

            if (dbTable.hasSymbol(symbol.getToken())) {
                Symbol dbSymbol = dbTable.getSymbol(symbol.getToken());
                if (!symbol.dbEquals(dbSymbol)) {
                    missmatchBuilder.add(symbol.getToken(), symbolDiffToJson(symbol, dbSymbol));
                }
                dbTable.removeSymbol(symbol.getToken());
            } else {
                if(symbol.isValid())
                    missingDBBuilder.add(symbol.getToken());
            }
        }

        List<Symbol> dbSymbols = dbTable.getSymbols().stream().filter(symbol -> select.contains(symbol.getType())).sorted(Comparator.comparing(Symbol::getToken)).collect(Collectors.toList());

        for (Symbol dbsymbol : dbSymbols) {
            missingLocalBuilder.add(dbsymbol.getToken());
        }
        tableBuilder.add(MISSMATCHES, missmatchBuilder);
        tableBuilder.add(NOT_FOUND_IN_DB, missingDBBuilder);
        tableBuilder.add(NOT_FOUND_IN_LOCAL, missingLocalBuilder);
        return tableBuilder.build();
    }

    private JsonObject symbolDiffToJson(Symbol symbol, Symbol dbSymbol) {
        JsonObjectBuilder symbolBuilder = Json.createObjectBuilder();

        if (symbol.getPrimaryModule() != null && dbSymbol.getPrimaryModule() != null && !symbol.getPrimaryModule().equals(dbSymbol.getPrimaryModule())) {
            JsonObjectBuilder primaryBuilder = Json.createObjectBuilder();
            primaryBuilder.add(KEY_LOCAL, symbol.getPrimaryModule().getName());
            primaryBuilder.add(KEY_DICTIONARY, dbSymbol.getPrimaryModule().getName());
            symbolBuilder.add(KEY_PRIMARY_VIEW, primaryBuilder);
        }

        if (symbol.getShadowModule() != null && dbSymbol.getShadowModule() != null && !symbol.getShadowModule().equals(dbSymbol.getShadowModule())) {

            JsonObjectBuilder shadowBuilder = Json.createObjectBuilder();

            JsonArrayBuilder shadowLocalBuilder = Json.createArrayBuilder();
            for (Module view : symbol.getShadowModule())
                shadowLocalBuilder.add(view.getName());
            shadowBuilder.add(KEY_LOCAL, shadowLocalBuilder);

            JsonArrayBuilder shadowDBBuilder = Json.createArrayBuilder();
            for (Module view : symbol.getShadowModule())
                shadowDBBuilder.add(view.getName());
            shadowBuilder.add(KEY_DICTIONARY, shadowDBBuilder);

            symbolBuilder.add(KEY_SHADOW_VIEWS, shadowBuilder);
        }
        if (symbol.getCategory() != null && dbSymbol.getCategory() != null && !symbol.getCategory().equals(dbSymbol.getCategory())) {

            JsonObjectBuilder categoryBuilder = Json.createObjectBuilder();
            categoryBuilder.add(KEY_LOCAL, symbol.getCategory().getName());
            categoryBuilder.add(KEY_DICTIONARY, dbSymbol.getCategory().getName());
            symbolBuilder.add(KEY_CATEGORY, categoryBuilder);
        }
        if (symbol.getUnits() != null && dbSymbol.getUnits() != null && !symbol.getUnits().equals(dbSymbol.getUnits())) {

            JsonObjectBuilder unitsBuilder = Json.createObjectBuilder();
            unitsBuilder.add(KEY_LOCAL, symbol.getUnits());
            unitsBuilder.add(KEY_DICTIONARY, dbSymbol.getUnits());
            symbolBuilder.add(KEY_UNITS, unitsBuilder);
        }
        if (symbol.getComment() != null && dbSymbol.getComment() != null && !symbol.getComment().equals(dbSymbol.getComment())) {

            JsonObjectBuilder commentBuilder = Json.createObjectBuilder();
            commentBuilder.add(KEY_LOCAL, symbol.getComment());
            commentBuilder.add(KEY_DICTIONARY, dbSymbol.getComment());
            symbolBuilder.add(KEY_COMMENT, commentBuilder);
        }
        if (symbol.getExcel() != null && dbSymbol.getExcel() != null && !symbol.getExcel().equals(dbSymbol.getExcel())) {

            JsonArrayBuilder localList = Json.createArrayBuilder();
            JsonArrayBuilder remoteList = Json.createArrayBuilder();

            for(ExcelRef excel : dbSymbol.getExcel()){
                localList.add(getExcelRefJson(excel));
            }
            for(ExcelRef excel : symbol.getExcel()){
                remoteList.add(getExcelRefJson(excel));
            }

            JsonObjectBuilder excelBuilder = Json.createObjectBuilder();

            excelBuilder.add(KEY_LOCAL, localList);
            excelBuilder.add(KEY_DICTIONARY, remoteList);
            symbolBuilder.add(KEY_EXCEL, excelBuilder);
        }

        return symbolBuilder.build();

    }

    private JsonObject modulesDiffToJson(Set<Module> modules, Set<Module> dbModules) {
        modules = modules.stream().filter(Module::isValid).collect(Collectors.toSet());
        JsonArrayBuilder missingLocalBuilder = Json.createArrayBuilder();
        JsonArrayBuilder missingDBBuilder = Json.createArrayBuilder();

        List<Module> localModules = modules.stream().sorted().collect(Collectors.toList());
        JsonObjectBuilder tableBuilder = Json.createObjectBuilder();
        for (Module module : localModules) {
            if (dbModules.contains(module))
                dbModules.remove(module);
            else
                missingDBBuilder.add(module.getName());
        }
        List<Module> dbModulesList = dbModules.stream().sorted().collect(Collectors.toList());

        for (Module dbModule : dbModulesList) {
            missingLocalBuilder.add(dbModule.getName());
        }

        tableBuilder.add(NOT_FOUND_IN_DB, missingDBBuilder);
        tableBuilder.add(NOT_FOUND_IN_LOCAL, missingLocalBuilder);
        return tableBuilder.build();
    }

    private JsonObject categoriesDiffToJson(List<Category> categories, List<Category> dbCategories) {
        categories = categories.stream().filter(Category::isValid).collect(Collectors.toList());

        JsonObjectBuilder tableBuilder = Json.createObjectBuilder();
        JsonObjectBuilder missmatchBuilder = Json.createObjectBuilder();
        JsonArrayBuilder missingLocalBuilder = Json.createArrayBuilder();
        JsonArrayBuilder missingDBBuilder = Json.createArrayBuilder();

        List<Category> localCategories = categories.stream().sorted(Comparator.comparing(Category::getName)).collect(Collectors.toList());

        for (Category category : localCategories) {

            List<Category> dbCategoryCopy = List.copyOf(dbCategories);
            boolean categoryInDb = false;
            for (Category dbCategory : dbCategoryCopy) {
                if (dbCategory.getName().equals(category.getName())) {
                    if (!dbCategory.equals(category)) {
                        missmatchBuilder.add(category.getWholeName(), categoryDiffToJson(category, dbCategory));
                    }
                    dbCategories.remove(category);
                    categoryInDb = true;
                }
            }
            if (!categoryInDb) {
                missingDBBuilder.add(category.getWholeName());

            }


        }
        List<Category> dbBCategoriesList = dbCategories.stream().sorted(Comparator.comparing(Category::getWholeName)).collect(Collectors.toList());


        for (Category dbCategory : dbBCategoriesList) {
            missingLocalBuilder.add(dbCategory.getWholeName());
        }

        tableBuilder.add(MISSMATCHES, missmatchBuilder);
        tableBuilder.add(NOT_FOUND_IN_DB, missingDBBuilder);
        tableBuilder.add(NOT_FOUND_IN_LOCAL, missingLocalBuilder);

        return tableBuilder.build();
    }

    private JsonObject categoryDiffToJson(Category category, Category dbCategory) {
        JsonObjectBuilder categoryBuilder = Json.createObjectBuilder();

        if (category.getSuperCategory() != null && dbCategory.getSuperCategory() == null) {
            JsonObjectBuilder missmatchBuilder = Json.createObjectBuilder();
            missmatchBuilder.add(KEY_LOCAL, category.getSuperCategory() == null ? "Subcategory" : "Category");
            missmatchBuilder.add(KEY_DICTIONARY, dbCategory.getSuperCategory() == null ? "Subcategory" : "Category");
            categoryBuilder.add("Missmatch", missmatchBuilder);
        } else {
            if (category.getSuperCategory() != null && dbCategory.getSuperCategory() != null && !category.getSuperCategory().getName().equals(dbCategory.getSuperCategory().getName())) {
                JsonObjectBuilder superBuilder = Json.createObjectBuilder();
                superBuilder.add(KEY_LOCAL, category.getSuperCategory().getName());
                superBuilder.add(KEY_DICTIONARY, dbCategory.getSuperCategory().getName());
                categoryBuilder.add(KEY_SUPER, superBuilder);
            }

            JsonObjectBuilder subcategoryBuilder = Json.createObjectBuilder();

            if (category.getSubcategories() != null && dbCategory.getSubcategories() != null && !category.getSubcategories().equals(dbCategory.getSubcategories())) {

                JsonArrayBuilder subcategoriesLocalBuilder = Json.createArrayBuilder();
                for (Category subcategory : category.getSubcategories())
                    subcategoriesLocalBuilder.add(subcategory.getName());
                subcategoryBuilder.add(KEY_LOCAL, subcategoriesLocalBuilder);

                JsonArrayBuilder subcategoriesDBBuilder = Json.createArrayBuilder();
                for (Category dbSubcategory : dbCategory.getSubcategories())
                    subcategoriesDBBuilder.add(dbSubcategory.getName());
                subcategoryBuilder.add(KEY_DICTIONARY, subcategoriesDBBuilder);

                categoryBuilder.add(KEY_SUBCATEGORY, subcategoryBuilder);

            }
        }
        return categoryBuilder.build();

    }

    public JsonArray build() {
        return fileBuilder.build();
    }

    private static JsonObjectBuilder getExcelRefJson( ExcelRef excel) {
        JsonObjectBuilder fileBuilder = Json.createObjectBuilder();
        fileBuilder.add(KEY_SHEET, excel.getSheet());
        fileBuilder.add(KEY_FILENAME, excel.getFilename());
        JsonArrayBuilder infoListBuilder = Json.createArrayBuilder();

        for (Triple<List<String>, String, String> info : excel.getCellRangeInformation()) {
            JsonObjectBuilder infoBuilder = Json.createObjectBuilder();

            if (!info.a.isEmpty()) {
                JsonArrayBuilder indexBuilder = Json.createArrayBuilder();
                for (String indexName : info.a) {
                    indexBuilder.add(indexName);
                }
                infoBuilder.add(KEY_INDEXES, indexBuilder);
            }
            infoBuilder.add(KEY_CELLRANGE, info.b);
            if (info.c != null)
                infoBuilder.add(KEY_SERIES, info.c);

            infoListBuilder.add(infoBuilder);
        }
        fileBuilder.add(KEY_INFO, infoListBuilder);


        return fileBuilder;
    }
}
