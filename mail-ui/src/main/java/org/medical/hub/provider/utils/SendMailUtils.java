package org.medical.hub.provider.utils;

import org.medical.hub.MedicalHubApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Pattern;


@Component
public class SendMailUtils {



    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalHubApplication.class);

    public static boolean isValidEmail(String email) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }
    public static void isEmailDomainValid(String email) {
        try {
            String domain = email.substring(email.lastIndexOf('@') + 1);
            int result = doLookup(domain);
            LOGGER.info("Domain: " + domain);
            LOGGER.info("Result of domain: " + result);
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
        }
    }
    public static int doLookup(String hostName) throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        DirContext ictx = new InitialDirContext( env );
        Attributes attrs = ictx.getAttributes( hostName, new String[] { "MX" });
        Attribute attr = attrs.get( "MX" );
        if( attr == null ) return( 0 );
        return( attr.size() );
    }


    public static String replacePlaceholders(String template, String placeholders){

        String[] pairs = placeholders.split("\\n");
        Map<String, String> values = new HashMap<>();
        for (String pair : pairs) {
            String[] parts = pair.split("=");
            values.put(parts[0].replace("[", "").replace("]", ""), parts[1]);
        }

        for (Map.Entry<String, String> entry : values.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        return template;
    }




}
