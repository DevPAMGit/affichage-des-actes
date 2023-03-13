package org.cd59.affichagedesactes.action.custom.envoi;

import org.springframework.beans.factory.InitializingBean;

public class WebServiceParametres implements InitializingBean {

    protected static String hote;
    
	public void setHote(String hote) { 
		WebServiceParametres.hote = hote;
	}

	public static String getHote() {
		return hote;
	}
    protected static String login;
    
	public void setLogin(String login) { 
		WebServiceParametres.login = login;
	}

	public static String getLogin() {
		return login;
	}
    protected static String motdepasse;
    
	public void setMotdepasse(String motdepasse) { 
		WebServiceParametres.motdepasse = motdepasse;
	}

	public static String getMotdepasse() {
		return motdepasse;
	}	
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Url de l'HOTE pour l'envoi à l'affichage par webservice  : " + hote);
	}

}
