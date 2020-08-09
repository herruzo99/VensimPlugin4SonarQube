package es.uva.locomotion.service;

import es.uva.locomotion.VensimPlugin;
import es.uva.locomotion.parser.Symbol;
import es.uva.locomotion.parser.SymbolTable;
import es.uva.locomotion.parser.SymbolType;
import es.uva.locomotion.utilities.Constants;
import es.uva.locomotion.utilities.exceptions.*;
import es.uva.locomotion.utilities.logs.LoggingLevel;
import es.uva.locomotion.utilities.logs.VensimLogger;


import java.util.List;
import java.util.stream.Collectors;


/**
 * Controller that handles the requests to services.
 * Has the responsibility of filtering the data that goes in the service and the data that the scanner receives.
 * Also handles and logs errors with the service.
 */
public class ServiceController {

    protected static VensimLogger LOG = VensimLogger.getInstance();
    private String token;
    private String dictionaryService;

    // I used to put this message in a separate log call, but there were another logs in between the two. So I decided to join them.
    private final String RULES_DISABLED_MESSAGE = "The rules that require the data from the dictionary service will be skipped.";
    private final String INVALID_URL_MESSAGE = "The url of the dictionary service is invalid (Missing protocol http:// or https://, invalid format or invalid protocol)\n"+ RULES_DISABLED_MESSAGE ;
    private final String MISSING_DICTIONARY_SERVICE_MESSAGE= "Missing dictionary service parameter.\n" + RULES_DISABLED_MESSAGE;
    private final String SERVICE_UNREACHABLE_MESSAGE = "The dictionary service was unreachable.\n" + RULES_DISABLED_MESSAGE;

    public ServiceController(String dictionaryService){
        this.dictionaryService = dictionaryService;
        token = null;
    }


    public void authenticate(String dictionaryUser, String dictionaryPassword) {
        try {
            token = DBFacade.getAuthenticationToken(dictionaryService, dictionaryUser, dictionaryPassword);
        } catch (InvalidServiceUrlException ex) {
            LOG.unique(INVALID_URL_MESSAGE, LoggingLevel.ERROR);
        } catch (EmptyServiceException ex) {
            LOG.unique(MISSING_DICTIONARY_SERVICE_MESSAGE, LoggingLevel.INFO);
        } catch (ConnectionFailedException ex) {
            LOG.unique(SERVICE_UNREACHABLE_MESSAGE, LoggingLevel.ERROR);
        }
    }

    /**
     * @param symbols List of symbols to be searched in the dictionary service. Functions and symbols defined by Vensim are ignored
     *                ({@link Constants#DEFAULT_VENSIM_SYMBOLS})
     * @return Symbol table containing the symbols found with the data (type, comments, indexes, etc) found in the dictionary.<br>
     * Returns null and logs a error if:
     *    <ul>
     *        <li>{@link ServiceController#dictionaryService} is empty or null,</li>
     *        <li>The dictionary service was unreachable (invalid domain, no connection, etc)</li>
     *        <li>The response from the service wasn't valid</li>
     *    </ul>
     */
    public SymbolTable getSymbolsFromDb(List<Symbol> symbols){
        if(!isAuthenticated())
            throw new NotAuthenticatedException();

        List<String> symbolsFound = symbols.stream().filter(this::hasToFetchSymbolFromDB).map(Symbol::getToken).collect(Collectors.toList());
        String logMessage="";
        try {
            return  DBFacade.getExistingSymbolsFromDB(dictionaryService, symbolsFound, token);
        }catch (InvalidServiceUrlException ex){
            LOG.unique(INVALID_URL_MESSAGE, LoggingLevel.ERROR);
            return null;
        }catch (EmptyServiceException ex){
            LOG.unique(MISSING_DICTIONARY_SERVICE_MESSAGE, LoggingLevel.INFO);
            return null;
        }catch (ConnectionFailedException ex){
            LOG.unique(SERVICE_UNREACHABLE_MESSAGE, LoggingLevel.ERROR);
            return null;
        }catch (ServiceResponseFormatNotValid ex){
           logMessage = "The response of the dictionary service wasn't valid. "+ ex.getMessage() + "\n"+
                   "To see the response use the analysis parameter: -Dvensim.logServerMessages=true \n" +
                    RULES_DISABLED_MESSAGE;

           LOG.error(logMessage);
            return null;
        }
    }

    public void injectNewSymbols( String module, List<Symbol> foundSymbols, SymbolTable dbSymbolTable){
        if(dbSymbolTable==null)
            return;

        if(!isAuthenticated())
            throw new NotAuthenticatedException();

        List<Symbol> newSymbols = foundSymbols.stream().filter(symbol -> !dbSymbolTable.hasSymbol(symbol.getToken().trim()) && hasToFetchSymbolFromDB(symbol))
                .collect(Collectors.toList());

        List<Symbol> validSymbols = newSymbols.stream().filter(Symbol::isValid).collect(Collectors.toList());

        if(validSymbols.size()>=1){
            try {
                DBFacade.injectSymbols(dictionaryService, module, validSymbols, token);
                List<String> tokensInjected = validSymbols.stream().map(Symbol::getToken).sorted(String::compareTo).collect(Collectors.toList());
                LOG.info("Injected symbols in module '" + module + "': " + tokensInjected);
            }catch (InvalidServiceUrlException ex){
                LOG.unique(INVALID_URL_MESSAGE, LoggingLevel.ERROR);
            }catch (EmptyServiceException ex){
                LOG.unique(MISSING_DICTIONARY_SERVICE_MESSAGE, LoggingLevel.INFO);
            }catch (ConnectionFailedException ex){
                LOG.unique(SERVICE_UNREACHABLE_MESSAGE, LoggingLevel.ERROR);
            }

        }

    }

    public boolean isAuthenticated(){
        return token != null;
    }



    private boolean hasToFetchSymbolFromDB(Symbol symbol){
        return !List.of(SymbolType.Function, SymbolType.Subscript_Value, SymbolType.UNDETERMINED, SymbolType.UNDETERMINED_FUNCTION).
                contains(symbol.getType()) && !Constants.DEFAULT_VENSIM_SYMBOLS.contains(symbol.getToken().trim()) &&
                !symbol.getDefinitionLines().isEmpty();

    }
}
