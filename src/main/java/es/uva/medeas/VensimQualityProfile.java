package es.uva.medeas;

import es.uva.medeas.rules.LookupNameCheck;
import es.uva.medeas.rules.SubscriptNameCheck;
import es.uva.medeas.rules.SubscriptValueNameCheck;

import es.uva.medeas.rules.VariableNameCheck;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;


public final class VensimQualityProfile implements BuiltInQualityProfilesDefinition {



    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile("Vensim Rules", VensimLanguage.KEY);
        profile.setDefault(true);

        String REPO_KEY = VensimRuleRepository.REPOSITORY_KEY;

        profile.activateRule(REPO_KEY, SubscriptNameCheck.CHECK_KEY);
        profile.activateRule(REPO_KEY, SubscriptValueNameCheck.CHECK_KEY);
        profile.activateRule(REPO_KEY, LookupNameCheck.CHECK_KEY);
        profile.activateRule(REPO_KEY, VariableNameCheck.CHECK_KEY);

        profile.done();
    }

}
