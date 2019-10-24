package es.uva.medeas;

import es.uva.medeas.rules.SubscriptConventionCheck;
import org.sonar.api.server.rule.RulesDefinition;
import es.uva.medeas.rules.NotEmptyCheck;

import java.util.HashSet;


public class VensimRuleRepository implements RulesDefinition{

    private static final String REPOSITORY_NAME = "VensimRules";
    public static final String REPOSITORY_KEY = "vensim";




    @Override
    public void define(RulesDefinition.Context context) {

        RulesDefinition.NewRepository repository = context
                .createRepository(REPOSITORY_KEY, VensimLanguage.KEY)
                .setName(REPOSITORY_NAME);

        repository.createRule(NotEmptyCheck.CHECK_KEY).setName("NotEmptyCheck").setMarkdownDescription("The file shouldn't be empty");
        repository.createRule(SubscriptConventionCheck.CHECK_KEY).setName("SubscriptConvention").setMarkdownDescription("Nope");//TODO
        repository.done();
    }



    public static Iterable<Class> getChecks(){
        HashSet<Class> set = new HashSet<>();
        set.add(NotEmptyCheck.class);
        set.add(SubscriptConventionCheck.class);
        return set;
    }

}
