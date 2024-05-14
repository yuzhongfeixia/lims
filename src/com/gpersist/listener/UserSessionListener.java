package com.gpersist.listener;

import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class UserSessionListener implements HttpSessionListener {

    private static java.util.Hashtable<String, HttpSession> users = new Hashtable<String, HttpSession>();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // TODO Auto-generated method stub

    }

    public synchronized static boolean isLogined(HttpSession session, String userid) {
        boolean rtn = false;

        if (users.containsKey(userid)) {
            rtn = true;
            HttpSession esession = users.get(userid);

            try {
                if (!session.getId().equals(esession.getId()))
                    esession.invalidate();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        users.remove(userid);
        users.put(userid, session);

        return rtn;
    }
}
