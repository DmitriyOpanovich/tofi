package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 19.10.2016.
 */
public class DB {
    public static List<UserState> userStates = new ArrayList<>();

    public static UserState getUserState(long chatId, int userId){
        for(UserState userState : userStates){
            if(userState.getChatId()==chatId && userState.getUserId()==userId){
                return userState;
            }
        }
        UserState userState = new UserState(userId, chatId);
        userStates.add(userState);
        return userState;
    }

    public static UserState getUserState(long chatId, int userId, int state){
        for(UserState userState : userStates){
            if(userState.getChatId()==chatId && userState.getUserId()==userId){
                return userState;
            }
        }
        UserState userState = new UserState(userId, chatId, state);
        userStates.add(userState);
        return userState;
    }


    public static UserState getUserState(int userId){
        for(UserState userState : userStates){
            if(userState.getUserId()==userId){
                return userState;
            }
        }
        return null;
    }

}
