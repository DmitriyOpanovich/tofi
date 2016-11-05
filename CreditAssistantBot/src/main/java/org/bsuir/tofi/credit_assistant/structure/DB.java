package org.bsuir.tofi.credit_assistant.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 19.10.2016.
 */
public class DB {
    public static List<UserAlert> userAlerts = new ArrayList<>();

    public static UserAlert getUser(long chatId, int userId){
        for(UserAlert userAlert : userAlerts){
            if(userAlert.getChatId()==chatId && userAlert.getUserId()==userId){
                return userAlert;
            }
        }
        UserAlert userAlert = new UserAlert(userId,chatId);
        userAlerts.add(userAlert);
        return userAlert;
    }

    public static UserAlert getUser(int userId){
        for(UserAlert userAlert : userAlerts){
            if(userAlert.getUserId()==userId){
                return userAlert;
            }
        }
        return null;
    }

}
