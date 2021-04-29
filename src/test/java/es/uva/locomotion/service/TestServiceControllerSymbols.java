package es.uva.locomotion.service;

import es.uva.locomotion.model.Module;
import es.uva.locomotion.model.symbol.Symbol;
import es.uva.locomotion.model.symbol.SymbolTable;
import es.uva.locomotion.model.symbol.SymbolType;
import es.uva.locomotion.testutilities.GeneralTestUtilities;
import es.uva.locomotion.testutilities.ServiceTestUtilities;
import es.uva.locomotion.utilities.Constants;
import es.uva.locomotion.utilities.exceptions.ConnectionFailedException;
import es.uva.locomotion.utilities.logs.LoggingLevel;
import es.uva.locomotion.utilities.logs.VensimLogger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;


public class TestServiceControllerSymbols {

    public static final String MODULE = "module";

    @After
    public void resetDbFacade() {
        DBFacade.handler = new ServiceConnectionHandler();
    }

    public ServiceController getAuthenticatedServiceController(String dictionaryService) {
        ServiceController controller = spy(new ServiceController(dictionaryService));
        doReturn(true).when(controller).isAuthenticated();
        doNothing().when(controller).authenticate(anyString(), anyString());
        return controller;

    }

    @Test
    public void testGetSymbolsControllerIgnoresFunctions() {
        ServiceConnectionHandler mockHandler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("[]");
        DBFacade.handler = mockHandler;

        ServiceController controller = getAuthenticatedServiceController("https://localhost");


        List<Symbol> symbols = Arrays.asList(new Symbol("func1", SymbolType.FUNCTION),
                new Symbol("var", SymbolType.VARIABLE),
                new Symbol("func2", SymbolType.FUNCTION),
                new Symbol("const", SymbolType.CONSTANT),
                new Symbol("lookup", SymbolType.LOOKUP_TABLE),
                new Symbol("subscript", SymbolType.SUBSCRIPT),
                new Symbol("sValue", SymbolType.SUBSCRIPT_VALUE),
                new Symbol("realityCheck", SymbolType.REALITY_CHECK),
                new Symbol("func3", SymbolType.FUNCTION)
        );

        for (Symbol s : symbols)
            s.addLine(1);

        controller.getSymbolsFromDb(symbols);

        verify(mockHandler, Mockito.times(1)).sendSymbolTableRequestToDictionaryService(any(), argThat(actualJson -> {
            JsonObject expectedJson = GeneralTestUtilities.getJsonObjectFromList("var", "const", "lookup", "subscript", "realityCheck");

            return actualJson.equals(expectedJson);

        }), any());
    }

    @Test
    public void testGetSymbolsControllerIgnoresDefaultSymbols() {
        ServiceConnectionHandler mockHandler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("[]");
        DBFacade.handler = mockHandler;

        ServiceController controller = getAuthenticatedServiceController("http://localhost");

        List<Symbol> symbols = Stream.of("FINAL TIME", "INITIAL TIME", "SAVEPER", "TIME STEP").map(Symbol::new).collect(Collectors.toList());


        controller.getSymbolsFromDb(symbols);

        verify(mockHandler, Mockito.times(1)).sendSymbolTableRequestToDictionaryService(any(), argThat(jsonObject -> jsonObject.toString().equals("{\"symbols\":[]}")), any());

    }

    @Test
    public void testGetSymbolsDictionaryInvalidServiceUrlMissingProtocol() {
        ServiceController controller = getAuthenticatedServiceController("www.myextremelyepicservice.com");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        SymbolTable actualValue = controller.getSymbolsFromDb(new ArrayList<>());

        Assert.assertNull(actualValue);
        verify(logger).unique("The url of the dictionary service is invalid (Missing protocol http:// or https://, invalid format or invalid protocol)\n" +
                "The rules that require the data from the dictionary service will be skipped.", LoggingLevel.ERROR);
    }

    @Test
    public void testGetSymbolsDictionaryInvalidServiceUrlInvalidFormat() {
        ServiceController controller = getAuthenticatedServiceController("http://\\$*^");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        SymbolTable actualValue = controller.getSymbolsFromDb(new ArrayList<>());


        Assert.assertNull(actualValue);
        verify(logger).unique("The url of the dictionary service is invalid (Missing protocol http:// or https://, invalid format or invalid protocol)\n" +
                "The rules that require the data from the dictionary service will be skipped.", LoggingLevel.ERROR);
    }

    @Test
    public void testGetSymbolsDictionaryInvalidServiceUrlInvalidProtocol() {
        ServiceController controller = getAuthenticatedServiceController("smtp://address:password@coolmail.com");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        SymbolTable actualValue = controller.getSymbolsFromDb(new ArrayList<>());

        Assert.assertNull(actualValue);
        verify(logger).unique("The url of the dictionary service is invalid (Missing protocol http:// or https://, invalid format or invalid protocol)\n" +
                "The rules that require the data from the dictionary service will be skipped.", LoggingLevel.ERROR);
    }

    @Test
    public void testGetSymbolsDictionaryMissingServiceEmptyUrl() {
        ServiceController controller = getAuthenticatedServiceController("");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        SymbolTable actualValue = controller.getSymbolsFromDb(new ArrayList<>());

        Assert.assertNull(actualValue);
        verify(logger).unique("Missing dictionary service parameter.\n" +
                "The rules that require the data from the dictionary service will be skipped.", LoggingLevel.INFO);
    }

    @Test
    public void testGetSymbolsDictionaryMissingServiceNullUrl() {
        ServiceController controller = getAuthenticatedServiceController(null);
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        SymbolTable actualValue = controller.getSymbolsFromDb(new ArrayList<>());

        Assert.assertNull(actualValue);
        verify(logger).unique("Missing dictionary service parameter.\n" +
                "The rules that require the data from the dictionary service will be skipped.", LoggingLevel.INFO);
    }

    @Test
    public void testGetSymbolsDictionaryConnectionFailed() {
        ServiceConnectionHandler handler = mock(ServiceConnectionHandler.class);
        when(handler.sendSymbolTableRequestToDictionaryService(any(), any(), any())).thenThrow(new ConnectionFailedException(null));
        DBFacade.handler = handler;

        ServiceController controller = getAuthenticatedServiceController("http://localhost");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        SymbolTable actualValue = controller.getSymbolsFromDb(new ArrayList<>());

        Assert.assertNull(actualValue);
        verify(logger).unique("The dictionary service was unreachable.\n" +
                "The rules that require the data from the dictionary service will be skipped.", LoggingLevel.ERROR);

    }

    @Test
    public void testGetSymbolsDictionaryInvalidFormatLiteralList() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("[1,2,3]");
        ServiceController controller = getAuthenticatedServiceController("http://localhost");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        SymbolTable actualValue = controller.getSymbolsFromDb(new ArrayList<>());
        Assert.assertNull(actualValue);
        verify(logger).error("The response of the dictionary service wasn't valid. Expected an object.\n" +
                "To see the response use the analysis parameter: -Dvensim.logServerMessages=true \nThe rules that require the data from the dictionary service will be skipped.");

    }


    @Test
    public void testGetSymbolsDictionaryInvalidFormatNotAnObject() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("[{\"symbol\":\"foo\"}]");

        ServiceController controller = getAuthenticatedServiceController("http://localhost");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        SymbolTable actualValue = controller.getSymbolsFromDb(new ArrayList<>());

        Assert.assertNull(actualValue);
        verify(logger).error("The response of the dictionary service wasn't valid. Expected an object.\n" +
                "To see the response use the analysis parameter: -Dvensim.logServerMessages=true \n" +
                "The rules that require the data from the dictionary service will be skipped.");
    }


    @Test
    public void testGetSymbolsDictionaryInvalidFormatMissingKey() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{\"randomKey\":\"foo\"}", "[]");

        ServiceController controller = getAuthenticatedServiceController("http://localhost");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        SymbolTable actualValue = controller.getSymbolsFromDb(new ArrayList<>());

        Assert.assertNull(actualValue);
        verify(logger).error("The response of the dictionary service wasn't valid. Missing 'symbols' field.\n" +
                "To see the response use the analysis parameter: -Dvensim.logServerMessages=true \n" +
                "The rules that require the data from the dictionary service will be skipped.");
    }


    @Test
    public void testGetSymbolsConsecutiveDifferentErrorsAreLogged() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{\"randomKey\":\"foo\"}", "[]");

        ServiceController controller = getAuthenticatedServiceController("http://localhost");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        controller.getSymbolsFromDb(new ArrayList<>());
        controller.getSymbolsFromDb(new ArrayList<>());
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{\"symbol\":\"foo\"}");
        controller.getSymbolsFromDb(new ArrayList<>());
        controller.getSymbolsFromDb(new ArrayList<>());


        verify(logger, atLeastOnce()).error("The response of the dictionary service wasn't valid. Missing 'symbols' field.\n" +
                "To see the response use the analysis parameter: -Dvensim.logServerMessages=true \n" +
                "The rules that require the data from the dictionary service will be skipped.");
    }

    @Test
    public void testInjectNewSymbolsRemovesFunctionsAndUndetermined() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{}");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;


        SymbolTable table = new SymbolTable();
        GeneralTestUtilities.addSymbolInLines(table, "function", SymbolType.FUNCTION, 1);
        GeneralTestUtilities.addSymbolInLines(table, "undetermined function", SymbolType.UNDETERMINED_FUNCTION, 2);
        GeneralTestUtilities.addSymbolInLines(table, "undetermined", SymbolType.UNDETERMINED, 3);


        ServiceController controller = getAuthenticatedServiceController("https://something");
        controller.injectNewSymbols(new ArrayList<>(table.getSymbols()), List.of( new Module(MODULE)), new SymbolTable());


        verify(logger, times(1)).info("No new symbols to inject");
        verify(DBFacade.handler, never()).injectSymbols(any(), any(), any());
    }

    @Test
    public void testInjectNewSymbolsNullDbSymbolTable() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{}");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;


        SymbolTable table = new SymbolTable();
        GeneralTestUtilities.addSymbolInLines(table, "  constant  ", SymbolType.CONSTANT, 1);

        ServiceController controller = getAuthenticatedServiceController("https://something");
        controller.injectNewSymbols(new ArrayList<>(table.getSymbols()), List.of(new Module(MODULE)), null);


        verify(logger, never()).info(anyString());
        verify(DBFacade.handler, never()).injectSymbols(any(), any(), any());
    }


    @Test
    public void testInjectNewSymbolsRemovesDefaultSymbols() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{}");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        SymbolTable table = new SymbolTable();
        for (String symbol : Constants.DEFAULT_VENSIM_SYMBOLS) {
            Symbol s = new Symbol(symbol, SymbolType.CONSTANT);
            s.addLine(1);
            table.addSymbol(s);
        }

        ServiceController controller = getAuthenticatedServiceController("https://something");
        controller.injectNewSymbols(new ArrayList<>(table.getSymbols()), List.of(new Module(MODULE)), new SymbolTable());


        verify(logger, times(1)).info("No new symbols to inject");
        verify(DBFacade.handler, never()).injectSymbols(any(), any(), any());

    }


    @Test
    public void testInjectNewSymbolsOnlyIncludesNewAndValidSymbols() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{}");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        SymbolTable foundTable = new SymbolTable();
        GeneralTestUtilities.addSymbolInLines(foundTable, "  constant  ", SymbolType.CONSTANT, MODULE, 1);
        GeneralTestUtilities.addSymbolInLines(foundTable, "variable", SymbolType.VARIABLE, MODULE, 2);
        GeneralTestUtilities.addSymbolInLines(foundTable, "swtich", SymbolType.SWITCHES, MODULE, 3);
        GeneralTestUtilities.addSymbolInLines(foundTable, "subscript", SymbolType.SUBSCRIPT, MODULE, 4);
        GeneralTestUtilities.addSymbolInLines(foundTable, "subscript value", SymbolType.SUBSCRIPT_VALUE, MODULE, 5);
        GeneralTestUtilities.addSymbolInLines(foundTable, "reality check", SymbolType.REALITY_CHECK, MODULE, 6);
        GeneralTestUtilities.addSymbolInLines(foundTable, "lookup table", SymbolType.LOOKUP_TABLE, MODULE, 7);
        GeneralTestUtilities.addSymbolInLines(foundTable, "Symbol found in db", SymbolType.CONSTANT, MODULE, 8);

        Symbol notValid = GeneralTestUtilities.addSymbolInLines(foundTable, "invalid symbol", SymbolType.CONSTANT, 9);
        notValid.setAsInvalid(this.getClass().getSimpleName());


        SymbolTable dbTable = new SymbolTable();
        dbTable.addSymbol(new Symbol("            Symbol found in db     ", SymbolType.CONSTANT));

        ServiceController controller = getAuthenticatedServiceController("https://something");
        controller.injectNewSymbols(new ArrayList<>(foundTable.getSymbols()), List.of(new Module(MODULE)), dbTable);


        verify(logger, times(1)).info("Injected symbols in module \"module\": [constant, lookup table, reality check, subscript, swtich, variable]");
    }

    @Test
    public void testInjectNewSymbolsEmptyService() {
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        ServiceController controller = getAuthenticatedServiceController("");

        SymbolTable foundTable = new SymbolTable();
        GeneralTestUtilities.addSymbolInLines(foundTable, "constant", SymbolType.CONSTANT, MODULE, 1);

        controller.injectNewSymbols(new ArrayList<>(foundTable.getSymbols()), List.of(new Module(MODULE)), new SymbolTable());


        verify(logger, times(1)).unique("Missing dictionary service parameter.\nThe rules that require the data from the dictionary service will be skipped."
                , LoggingLevel.INFO);
    }

    @Test
    public void testInjectNewSymbolsNullService() {
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        ServiceController controller = getAuthenticatedServiceController(null);

        SymbolTable foundTable = new SymbolTable();
        GeneralTestUtilities.addSymbolInLines(foundTable, "constant", SymbolType.CONSTANT, MODULE, 1);

        controller.injectNewSymbols(new ArrayList<>(foundTable.getSymbols()), List.of(new Module(MODULE)), new SymbolTable());


        verify(logger, times(1)).unique("Missing dictionary service parameter.\nThe rules that require the data from the dictionary service will be skipped."
                , LoggingLevel.INFO);
    }

    @Test
    public void testInjectNewSymbolsConnectionFailed() {
        ServiceConnectionHandler handler = mock(ServiceConnectionHandler.class);
        when(handler.injectSymbols(any(), any(), any())).thenThrow(new ConnectionFailedException(null));
        DBFacade.handler = handler;

        ServiceController controller = getAuthenticatedServiceController("http://localhost");
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        SymbolTable foundTable = new SymbolTable();
        GeneralTestUtilities.addSymbolInLines(foundTable, "constant", SymbolType.CONSTANT, MODULE, 1);

        controller.injectNewSymbols(new ArrayList<>(foundTable.getSymbols()), List.of(new Module(MODULE)), new SymbolTable());


        verify(logger, times(1)).unique("The dictionary service was unreachable.\nThe rules that require the data from the dictionary service will be skipped.", LoggingLevel.ERROR);
    }


    @Test
    public void testInjectNewSymbolsMissingServiceProtocol() {
        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        ServiceController controller = getAuthenticatedServiceController("www.google.com");

        SymbolTable foundTable = new SymbolTable();
        GeneralTestUtilities.addSymbolInLines(foundTable, "constant", SymbolType.CONSTANT, MODULE, 1);

        controller.injectNewSymbols(new ArrayList<>(foundTable.getSymbols()), List.of(new Module(MODULE)), new SymbolTable());


        verify(logger, times(1))
                .unique("The url of the dictionary service is invalid (Missing protocol http:// or https://, invalid format or invalid protocol)\nThe rules that require the data from the dictionary service will be skipped.", LoggingLevel.ERROR);
    }


    @Test
    public void testInjectNewSymbolsNotDefinedInAnyLine() {
        DBFacade.handler = ServiceTestUtilities.getMockDbServiceHandlerThatReturns("{}");

        VensimLogger logger = Mockito.mock(VensimLogger.class);
        ServiceController.logger = logger;

        ServiceController controller = getAuthenticatedServiceController("https://www.google.com");

        SymbolTable foundTable = new SymbolTable();
        foundTable.addSymbol(new Symbol("constant", SymbolType.CONSTANT));

        controller.injectNewSymbols(new ArrayList<>(foundTable.getSymbols()), List.of(new Module(MODULE)), new SymbolTable());


        verify(logger, times(1)).info("No new symbols to inject");
        verify(DBFacade.handler, never()).injectSymbols(any(), any(), any());
    }
}

