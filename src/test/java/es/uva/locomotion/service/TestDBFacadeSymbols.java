package es.uva.locomotion.service;

import es.uva.locomotion.model.Module;
import es.uva.locomotion.model.category.Category;
import es.uva.locomotion.model.symbol.Symbol;
import es.uva.locomotion.model.symbol.SymbolTable;
import es.uva.locomotion.model.symbol.SymbolType;
import es.uva.locomotion.testutilities.ServiceTestUtilities;
import es.uva.locomotion.utilities.exceptions.EmptyServiceException;
import es.uva.locomotion.utilities.exceptions.InvalidServiceUrlException;
import es.uva.locomotion.utilities.exceptions.ServiceResponseFormatNotValid;
import es.uva.locomotion.utilities.logs.VensimLogger;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class TestDBFacadeSymbols {

    @After
    public void resetDbServiceHandler() {
        DBFacade.handler = new ServiceConnectionHandler(); // Makes the tests independent
    }
    
    @Test
    public void testGetSymbolsParsedJson() {
        SymbolTable dbTable = new SymbolTable();

        Symbol index1 = new Symbol("index1");
        index1.setType(SymbolType.SUBSCRIPT);
        index1.setComment("index1 comment");
        dbTable.addSymbol(index1);
        Symbol index2 = new Symbol("index2");
        index2.setType(SymbolType.SUBSCRIPT);
        dbTable.addSymbol(index2);

        Symbol foo = new Symbol("foo");
        foo.setUnits("foo unit");
        foo.setComment("foo comment");
        foo.setPrimaryModule(new Module("module 1"));
        foo.setCategory(Category.create("foo category"));
        foo.addShadowModule(new Module("module 2"));
        foo.setType(SymbolType.CONSTANT);
        dbTable.addSymbol(foo);

        Symbol var = new Symbol("var");
        var.setUnits("var unit");
        var.setComment("var comment");
        var.setPrimaryModule(new Module("module 3"));
        var.setCategory(Category.create("var category"));
        var.addShadowModule(new Module("module 4"));
        var.addIndexLine(List.of(index1, index2));
        var.setType(SymbolType.VARIABLE);
        dbTable.addSymbol(var);

        String jsonDbTable = "{\"symbols\":[" +
                "{\"name\":\"foo\", \"category\":\"foo category\", \"modules\":{\"main\":\"module 1\", \"secondary\":[\"module 2\"]}," +
                "\"programmingSymbolType\": \"CONSTANT\",\"indexes\":[], \"definition\": \"foo comment\", \"unit\":\"foo unit\"}," +
                "{\"name\":\"var\",\"programmingSymbolType\":\"VARIABLE\",\"unit\":\"var unit\", \"definition\":\"var comment\", \"modules\":{\"main\":\"module 3\", \"secondary\":[\"module 4\"]}, \"category\":\"var category\",\"indexes\":[\"index1\",\"index2\"]}" +
                "]}";

        String jsonIndexDbTable =   "[{\"indexName\":\"index1\", \"values\":[],\"definition\":\"index1 comment\"}," +
                "{\"indexName\":\"index2\", \"values\":[],\"definition\":\"\"}]";
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable,jsonIndexDbTable);

        SymbolTable obtainedTable = DBFacade.getExistingSymbolsAndIndexesFromDB("", List.of("foo", "var"), "token");
        assertEquals(dbTable, obtainedTable);
    }

    @Test
    public void testGetSymbolsLoadIndexValues() {
        SymbolTable dbTable = new SymbolTable();

        Symbol index = new Symbol("index", SymbolType.SUBSCRIPT);
        Symbol first_value = new Symbol("first value", SymbolType.SUBSCRIPT_VALUE);
        Symbol second_value = new Symbol("second value", SymbolType.SUBSCRIPT_VALUE);
        index.addDependency(first_value);
        index.addDependency(second_value);

        dbTable.addSymbol(index);
        dbTable.addSymbol(first_value);
        dbTable.addSymbol(second_value);

        String jsonDbTable = "{\"symbols\":[]}";
        String jsonIndexDbTable =   "[{\"indexName\":\"index\", \"definition\":\"\",\"values\":[\"first value\", \"second value\"]}]";


        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable,jsonIndexDbTable);

        SymbolTable obtainedTable = DBFacade.getExistingSymbolsAndIndexesFromDB("", List.of("foo", "var"), "token");

        assertEquals(dbTable, obtainedTable);
    }


    @Test
    public void testGetSymbolsOnlySymbols() {
        SymbolTable dbTable = new SymbolTable();

        Symbol foo = new Symbol("foo");
        foo.setUnits("foo unit");
        foo.setComment("foo comment");
        foo.setPrimaryModule(new Module("module 1"));
        foo.setCategory(Category.create("foo category"));
        foo.addShadowModule(new Module("module 2"));
        foo.setType(SymbolType.CONSTANT);
        dbTable.addSymbol(foo);


        String jsonDbTable = "{\"symbols\":[" +
                "{\"name\":\"foo\", \"category\":\"foo category\", \"modules\":{\"main\":\"module 1\", \"secondary\":[ \"module 2\"]}," +
                "\"programmingSymbolType\": \"CONSTANT\",\"indexes\":[], \"definition\": \"foo comment\", \"unit\":\"foo unit\"}]," +
                "\"indexes\":[]}";

        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable);

        SymbolTable obtainedTable = DBFacade.getExistingSymbolsFromDB("", List.of("foo", "var"), "token");

        assertEquals(dbTable, obtainedTable);
    }


    @Test
    public void testGetSymbolsEmptyResponse() {
        String jsonDbTable = "{\"symbols\":[],\"indexes\":[],\"categories\":[],\"modules\":[]}";


        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable);

        SymbolTable obtainedTable = DBFacade.getExistingSymbolsFromDB("", List.of("foo", "var"), "token");

        assertEquals(new SymbolTable(), obtainedTable);

    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetSymbolsSymbolListIsNull() {
        DBFacade.getExistingSymbolsFromDB("https://localhost", null, "token");
    }

    @Test
    public void testGetSymbolsUnexpectedFormatNoSymbolField() {
        String jsonDbTable = "{\"module\":\"\"}";

        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable);
        ServiceResponseFormatNotValid ex = assertThrows(ServiceResponseFormatNotValid.class, () -> DBFacade.getExistingSymbolsFromDB("http://localhost", List.of("foo", "var"), "token"));
        assertEquals("Missing 'symbols' field.", ex.getMessage());
        assertEquals(jsonDbTable, ex.getServiceResponse());
    }

    @Test(expected = ServiceResponseFormatNotValid.class)
    public void testGetSymbolsUnexpectedFormatArrayOfLiterals() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("[1,3,4]");

        DBFacade.getExistingSymbolsFromDB("http://localhost", List.of("foo", "var"), "token");
    }

    @Test(expected = ServiceResponseFormatNotValid.class)
    public void testGetSymbolsUnexpectedFormatNotAnObject() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("[{\"symbol\":\"foo\"}]");

        DBFacade.getExistingSymbolsFromDB("http://localhost", List.of("foo"), "token");

    }

    @Test
    public void testGetSymbolsUnexpectedFormatSymbolDoesntHaveNameKey() {
        String jsonDbTable = "{\"symbols\":[" +
                "{\"definition\":\"var comment\",\"unit\":\"\",\"category\":\"\",\"modules\":[],\"programmingSymbolType\":\"CONSTANT\",\"indexes\":[]}]," +
                "\"indexes\":[]}";

        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable, "[]");
        ServiceResponseFormatNotValid ex = assertThrows(ServiceResponseFormatNotValid.class, () -> DBFacade.getExistingSymbolsAndIndexesFromDB("http://localhost", List.of("foo", "var"), "token"));
        assertEquals("Missing 'name' field from a symbol.", ex.getMessage());
        assertEquals(jsonDbTable, ex.getServiceResponse());

    }

    @Test
    public void testGetSymbolsUnexpectedFormatSymbolDoesntHaveDefinitionKey() {
        String jsonDbTable = "{\"symbols\":[" +
                "{\"name\":\"var\",\"unit\":\"\",\"category\":\"\",\"modules\":[],\"programmingSymbolType\":\"CONSTANT\",\"indexes\":[]}]," +
                "\"indexes\":[]}";
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable, "[]");

        ServiceResponseFormatNotValid ex = assertThrows(ServiceResponseFormatNotValid.class, () -> DBFacade.getExistingSymbolsAndIndexesFromDB("http://localhost", List.of("foo", "var"), "token"));
        assertEquals("Missing 'definition' field in symbol 'var'.", ex.getMessage());
        assertEquals(jsonDbTable, ex.getServiceResponse());

    }

    @Test
    public void testGetSymbolsUnexpectedFormatDoesntHaveUnitKey() {
        String jsonDbTable = "{\"symbols\":[" +
                "{\"name\":\"var\",\"definition\":\"var comment\",\"category\":\"\",\"modules\":[],\"programmingSymbolType\":\"CONSTANT\",\"indexes\":[]}]," +

                "\"indexes\":[]}";
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable);

        ServiceResponseFormatNotValid ex = assertThrows(ServiceResponseFormatNotValid.class, () -> DBFacade.getExistingSymbolsFromDB("http://localhost", List.of("foo", "var"), "token"));
        assertEquals("Missing 'unit' field in symbol 'var'.", ex.getMessage());
        assertEquals(jsonDbTable, ex.getServiceResponse());

    }

    @Test
    public void testGetSymbolsUnexpectedFormatDoesntHaveCategoryKey() {
        String jsonDbTable = "{\"symbols\":[" +
                "{\"name\":\"var\",\"unit\":\"\",\"definition\":\"first comment\",\"modules\":[],\"programmingSymbolType\":\"CONSTANT\",\"indexes\":[]}]," +
                "\"indexes\":[]}";
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable);

        ServiceResponseFormatNotValid ex = assertThrows(ServiceResponseFormatNotValid.class, () -> DBFacade.getExistingSymbolsFromDB("http://localhost", List.of("foo", "var"), "token"));
        assertEquals("Missing 'category' field in symbol 'var'.", ex.getMessage());
        assertEquals(jsonDbTable, ex.getServiceResponse());

    }

    @Test
    public void testGetSymbolsUnexpectedFormatDoesntHaveModulesKey() {
        String jsonDbTable = "{\"symbols\":[" +
                "{\"name\":\"var\",\"unit\":\"\",\"definition\":\"first comment\",\"category\":\"\",\"programmingSymbolType\":\"CONSTANT\",\"indexes\":[]}]," +
                "\"indexes\":[]}";
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable);

        ServiceResponseFormatNotValid ex = assertThrows(ServiceResponseFormatNotValid.class, () -> DBFacade.getExistingSymbolsFromDB("http://localhost", List.of("foo", "var"), "token"));
        assertEquals("Missing 'modules' field in symbol 'var'.", ex.getMessage());
        assertEquals(jsonDbTable, ex.getServiceResponse());

    }

    @Test
    public void testGetSymbolsUnexpectedFormatDoesntHaveProgrammingSymbolTypeKey() {
        String jsonDbTable = "{\"symbols\":[" +
                "{\"name\":\"var\",\"unit\":\"\",\"definition\":\"first comment\",\"category\":\"\",\"modules\":[],\"indexes\":[]}]," +
                "\"indexes\":[]}";
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable);

        ServiceResponseFormatNotValid ex = assertThrows(ServiceResponseFormatNotValid.class, () -> DBFacade.getExistingSymbolsFromDB("http://localhost", List.of("foo", "var"), "token"));
        assertEquals("Missing 'programmingSymbolType' field in symbol 'var'.", ex.getMessage());
        assertEquals(jsonDbTable, ex.getServiceResponse());

    }

    @Test
    public void testGetSymbolsUnexpectedFormatDoesntHaveSymbolIndexesKey() {
        String jsonDbTable = "{\"symbols\":[" +
                "{\"name\":\"var\",\"unit\":\"\",\"definition\":\"first comment\",\"category\":\"\",\"modules\":[],\"programmingSymbolType\":\"CONSTANT\"}]," +
                "\"indexes\":[]}";
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable);

        ServiceResponseFormatNotValid ex = assertThrows(ServiceResponseFormatNotValid.class, () -> DBFacade.getExistingSymbolsFromDB("http://localhost", List.of("foo", "var"), "token"));
        assertEquals("Missing 'indexes' field in symbol 'var'.", ex.getMessage());
        assertEquals(jsonDbTable, ex.getServiceResponse());

    }

    @Test
    public void testGetSymbolsUnexpectedFormatUnknownProgrammingType() {
        String jsonDbTable = "{\"symbols\":[" +
                "{\"name\":\"var\",\"unit\":\"\",\"definition\":\"first comment\",\"category\":\"\",\"modules\":[],\"programmingSymbolType\":\"Unexpected random type\",\"indexes\":[]}]," +
                "\"indexes\":[]}";
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable);

        ServiceResponseFormatNotValid ex = assertThrows(ServiceResponseFormatNotValid.class, () -> DBFacade.getExistingSymbolsFromDB("http://localhost", List.of("foo", "var"), "token"));
        assertEquals("The symbol 'var' has an unknown programming type: 'Unexpected random type'", ex.getMessage());
        assertEquals(jsonDbTable, ex.getServiceResponse());

    }

    @Test
    public void testGetSymbolsResponseJsonContainsDuplicatedSymbols() {

        String jsonDbTable = "{\"symbols\":[" +
                "{\"name\":\"var\",\"unit\":\"\", \"definition\":\"first comment\", \"category\":\"\",\"modules\":{\"main\":\"foo\", \"secondary\":[]}, \"programmingSymbolType\":\"CONSTANT\",\"indexes\":[]}, " +
                "{\"name\":\"var\",\"unit\":\"other units\", \"definition\":\"other comment\", \"category\":\"\",\"modules\":[], \"programmingSymbolType\":\"CONSTANT\",\"indexes\":[]}], " +
                "\"indexes\":[]}";
        VensimLogger logger = mock(VensimLogger.class);
        DBFacade.logger = logger;
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable);


        DBFacade.getExistingSymbolsFromDB("http://localhost", List.of("var"), "token");


        verify(logger).info("Received duplicated symbol 'var' from the dictionary service.");
    }

    @Test
    public void testGetSymbolsUnexpectedFormatIndexDoesntHaveNameKey() {
        String jsonDbTable = "[{\"definition\":\"\",\"values\":[\"first value\",\"second value\"]}]";
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable);

        ServiceResponseFormatNotValid ex = assertThrows(ServiceResponseFormatNotValid.class, () -> DBFacade.getExistingIndexesFromDB("http://localhost", "token"));
        assertEquals("Missing 'indexName' field from an index.", ex.getMessage());
        assertEquals(jsonDbTable, ex.getServiceResponse());

    }

    @Test
    public void testGetSymbolsUnexpectedFormatIndexDoesntHaveDefinitionKey() {
        String jsonDbTable = "{\"symbols\":[]}";
              String jsonIndexDbTable =   "[{\"indexName\":\"index\",\"values\":[\"first value\",\"second value\"]}]";
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable, jsonIndexDbTable);

        ServiceResponseFormatNotValid ex = assertThrows(ServiceResponseFormatNotValid.class, () -> DBFacade.getExistingSymbolsAndIndexesFromDB("http://localhost", List.of("foo", "var"), "token"));
        assertEquals("Missing 'definition' field in the index 'index'.", ex.getMessage());
        assertEquals(jsonIndexDbTable, ex.getServiceResponse());

    }

    @Test
    public void testGetSymbolsUnexpectedFormatIndexDoesntHaveValuesKey() {
        String jsonDbTable = "[{\"indexName\":\"index\",\"definition\":\"index comment\"}]";
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns(jsonDbTable);

        ServiceResponseFormatNotValid ex = assertThrows(ServiceResponseFormatNotValid.class, () -> DBFacade.getExistingIndexesFromDB("http://localhost", "token"));
        assertEquals("Missing 'values' field in the index 'index'.", ex.getMessage());
        assertEquals(jsonDbTable, ex.getServiceResponse());

    }


    @Test(expected = ServiceResponseFormatNotValid.class)
    public void testGetSymbolsResponseIsntAJson() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("some text");

        DBFacade.getExistingSymbolsFromDB("http://localhost", List.of("foo", "var"), "token");
    }


    @Test(expected = EmptyServiceException.class)
    public void testGetSymbolsEmptyServiceRaisesException() {
        DBFacade.getExistingSymbolsFromDB("", List.of("foo", "var"), "token");
    }

    @Test(expected = EmptyServiceException.class)
    public void testGetSymbolsNullServiceRaisesException() {
        DBFacade.getExistingSymbolsFromDB(null, List.of("foo", "var"), "token");
    }

    @Test(expected = InvalidServiceUrlException.class)
    public void testGetSymbolsServiceWithAnotherProtocol() {
        DBFacade.getExistingSymbolsFromDB("ftp://somedomain/folder/file.txt", List.of("foo", "var"), "token");
    }

    @Test(expected = InvalidServiceUrlException.class)
    public void testGetSymbolsServiceWithInvalidProtocol() {
        DBFacade.getExistingSymbolsFromDB("\\some$randomtext", List.of("foo", "var"), "token");
    }

    @Test(expected = InvalidServiceUrlException.class)
    public void testGetSymbolsDomainWithoutProtocol() {
        DBFacade.getExistingSymbolsFromDB("www.google.com", List.of("foo", "var"), "token");
    }


    @Test
    public void testGetSymbolsDomainWithoutWWW() throws IOException, InterruptedException {
        ServiceConnectionHandler handler = new ServiceConnectionHandler();
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        doReturn(mockResponse).when(mockClient).send(any(), any());
        when(mockResponse.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(mockResponse.body()).thenReturn("{\"symbols\":[],\"indexes\":[],\"categories\":[],\"modules\":[]}");

        handler.client = mockClient;
        DBFacade.handler = handler;

        DBFacade.getExistingSymbolsFromDB("http://google.com", List.of("foo", "var"), "token");
    }

    @Test
    public void testInjectSymbol() {
        ServiceConnectionHandler handler = Mockito.mock(ServiceConnectionHandler.class);
        DBFacade.handler = handler;

        List<Symbol> symbols = new ArrayList<>();

        Symbol constant = new Symbol("    constant     ", SymbolType.CONSTANT);
        constant.setUnits("          constant units           ");
        constant.setComment("            constant comment         ");
        constant.setCategory(Category.create("           constant category      "));
        constant.addIndexLine(List.of(mock(Symbol.class)));

        symbols.add(constant);

        JsonReader jsonReader = Json.createReader(new StringReader("{\"symbols\": [{\"name\":\"constant\",\"unit\":\"constant units\",\"definition\":\"constant comment\",\"isIndexed\":\"true\",\"category\":\"constant category\",\"programmingsymboltype\":\"CONSTANT\"}], \"module\" : \"module\"}"));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        DBFacade.injectSymbols("http://www.gaagle.com", symbols,"module", "token");
        verify(handler).injectSymbols("http://www.gaagle.com", object, "token");
    }

    @Test
    public void testInjectSubscript() {
        ServiceConnectionHandler handler = Mockito.mock(ServiceConnectionHandler.class);
        DBFacade.handler = handler;

        List<Symbol> symbols = new ArrayList<>();

        Symbol subscript = new Symbol("    subscript     ", SymbolType.SUBSCRIPT);
        subscript.addDependency(new Symbol("           value1         ", SymbolType.SUBSCRIPT_VALUE));
        subscript.addDependency(new Symbol("           value2          ", SymbolType.SUBSCRIPT_VALUE));

        symbols.add(subscript);

        JsonReader jsonReader = Json.createReader(new StringReader("{\"indexes\":[{\"indexName\":\"subscript\",\"values\":[\"value1\",\"value2\"],\"definition\":\"\"}]}"));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        DBFacade.injectIndexes("http://www.gaagle.com", symbols, "token");
        verify(handler).injectIndexes("http://www.gaagle.com", object, "token");
    }

    @Test
    public void testInjectSubscriptValue() {
        ServiceConnectionHandler handler = Mockito.mock(ServiceConnectionHandler.class);
        DBFacade.handler = handler;

        List<Symbol> symbols = new ArrayList<>();
        symbols.add(new Symbol("value", SymbolType.SUBSCRIPT_VALUE));

        JsonReader jsonReader = Json.createReader(new StringReader("{\"indexes\":[]}"));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        DBFacade.injectIndexes("http://www.gaagle.com", symbols,"token");
        verify(handler).injectIndexes("http://www.gaagle.com", object, "token");
    }

    @Test
    public void testInjectVariable() {
        ServiceConnectionHandler handler = Mockito.mock(ServiceConnectionHandler.class);
        DBFacade.handler = handler;

        List<Symbol> symbols = new ArrayList<>();

        Symbol s = new Symbol("variable", SymbolType.VARIABLE);
        s.setCategory(Category.create("name"));

        symbols.add(s);

        JsonReader jsonReader = Json.createReader(new StringReader("{\"symbols\": [{\"name\":\"variable\",\"unit\":\"\",\"definition\":\"\",\"isIndexed\":\"false\",\"category\":\"name\",\"programmingsymboltype\":\"VARIABLE\"}], \"module\" : \"module\"}"));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        DBFacade.injectSymbols("http://www.gaagle.com", symbols, "module","token");
        verify(handler).injectSymbols("http://www.gaagle.com", object, "token");
    }

    @Test
    public void testInjectFunction() {
        ServiceConnectionHandler handler = Mockito.mock(ServiceConnectionHandler.class);
        DBFacade.handler = handler;

        List<Symbol> symbols = new ArrayList<>();
        symbols.add(new Symbol("function", SymbolType.FUNCTION));

        JsonReader jsonReader = Json.createReader(new StringReader("{\"symbols\": [], \"module\" : \"module\"}"));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        DBFacade.injectSymbols("http://www.gaagle.com", symbols, "module","token");
        verify(handler).injectSymbols("http://www.gaagle.com", object, "token");
    }

    @Test
    public void testInjectRealityCheck() {
        ServiceConnectionHandler handler = Mockito.mock(ServiceConnectionHandler.class);
        DBFacade.handler = handler;

        List<Symbol> symbols = new ArrayList<>();
        Symbol s = new Symbol("reality check", SymbolType.REALITY_CHECK);
        s.setCategory(Category.create("name"));
        symbols.add(s);

        JsonReader jsonReader = Json.createReader(new StringReader("{\"symbols\": [{\"name\":\"reality check\",\"unit\":\"\",\"definition\":\"\",\"isIndexed\":\"false\",\"category\":\"name\",\"programmingsymboltype\":\"REALITY_CHECK\"}], \"module\" : \"module\"}"));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        DBFacade.injectSymbols("http://www.gaagle.com", symbols, "module","token");
        verify(handler).injectSymbols("http://www.gaagle.com", object, "token");
    }

    @Test
    public void testInjectLookupTable() {
        ServiceConnectionHandler handler = Mockito.mock(ServiceConnectionHandler.class);
        DBFacade.handler = handler;

        List<Symbol> symbols = new ArrayList<>();
        Symbol s = new Symbol("lookup table", SymbolType.LOOKUP_TABLE);
        s.setCategory(Category.create("name"));
        symbols.add(s);

        JsonReader jsonReader = Json.createReader(new StringReader("{\"symbols\": [{\"name\":\"lookup table\",\"unit\":\"\",\"definition\":\"\",\"isIndexed\":\"false\",\"category\":\"name\",\"programmingsymboltype\":\"LOOKUP_TABLE\"}], \"module\" : \"module\"}"));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        DBFacade.injectSymbols("http://www.gaagle.com", symbols, "module","token");
        verify(handler).injectSymbols("http://www.gaagle.com", object, "token");
    }


    @Test
    public void testInjectSwitch() {
        ServiceConnectionHandler handler = Mockito.mock(ServiceConnectionHandler.class);
        DBFacade.handler = handler;

        List<Symbol> symbols = new ArrayList<>();
        Symbol s = new Symbol("switch", SymbolType.SWITCHES);
        s.setCategory(Category.create("name"));
        symbols.add(s);

        JsonReader jsonReader = Json.createReader(new StringReader("{\"symbols\": [{\"name\":\"switch\",\"unit\":\"\",\"definition\":\"\",\"isIndexed\":\"false\",\"category\":\"name\",\"programmingsymboltype\":\"SWITCHES\"}], \"module\" : \"module\"}"));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        DBFacade.injectSymbols("http://www.gaagle.com", symbols, "module","token");
        verify(handler).injectSymbols("http://www.gaagle.com", object, "token");
    }

}
