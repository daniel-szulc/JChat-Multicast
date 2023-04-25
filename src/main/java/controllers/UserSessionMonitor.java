package controllers;

import abstracts.IChatMember;
import models.User;
import utils.MessageType;
import utils.UserStatus;

import static utils.Constants.ACTIVITY_INTERVAL;

public class UserSessionMonitor extends Thread{

    IChatMember chatMember;

    public UserSessionMonitor(IChatMember chatMember){
        this.chatMember = chatMember;
    }


    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                try {
                    wait(ACTIVITY_INTERVAL);
                } catch (InterruptedException e) {
                    return;
                }
            }

            for (User user : chatMember.getUserManager().getAllUsers()) {
                if (user.getLastActivity() == null) {
                    continue;
                }
                long currentTime = System.currentTimeMillis();
                if(currentTime - user.getLastActivity() > ACTIVITY_INTERVAL * 5) {
                    user.setStatus(UserStatus.DISCONNECTED);
                }
                else if (currentTime - user.getLastActivity() > ACTIVITY_INTERVAL * 3) {
                        user.setStatus(UserStatus.INACTIVE);
                } else {
                    user.setStatus(UserStatus.ACTIVE);
                }
                chatMember.sendMessage(MessageType.ACTIVITY_CONFIRMATION);
            }
            chatMember.updateUsersList();
        }
    }


}
