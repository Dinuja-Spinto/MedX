package com.devStack.utill;

import com.devStack.db.Database;
import com.devStack.dto.User;

public class Cookie {
    public static User selectedUser= Database.userTable.get(1);
}
