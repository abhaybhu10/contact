package service;

/**
 * Created by kuabhay on 01/02/19
 */

import contact.ContactImpl;
import contact.RequestFilter;
import excpetion.AppExceptionMapper;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import persister.DDBClient;
import persister.DDBPersister;

/**
 * Created by kuabhay on 1/12/19
 */
public class ContactApp extends Application<Configuration> {
    @Override
    public void run(Configuration configuration, Environment environment) {
        environment.jersey()
            .register(new ContactResource(new ContactImpl(new DDBPersister(DDBClient.getDdbMapper()))));
        environment.jersey().register(new AppExceptionMapper());
        environment.jersey().register(new RequestFilter());
    }

    public static void main(String[] args) throws Exception {
        String[] arguments = {"server"};
        new ContactApp().run(arguments);
    }
}
