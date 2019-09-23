package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.User;
import utils.DBUtil;

public class UserValidator {
    public static List<String> validate(User u, Boolean name_duplicate_check_flag, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();

        String exname_error = _validateExName(u.getName(), name_duplicate_check_flag);
        if(!exname_error.equals("")) {
            errors.add(exname_error);
        }



        String name_error = _validateName(u.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = _validatePassword(u.getPassword(), password_check_flag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;
    }

//    ユーザー名の重複
 private static String _validateExName(String name, Boolean name_duplicate_check_flag) {


        // 既に登録されている社員番号との重複チェック
        if(name_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long name_count = (long)em.createNamedQuery("checkRegisteredName", Long.class)
                                           .setParameter("name", name)
                                             .getSingleResult();
            em.close();
            if(name_count > 0) {
                return "入力されたユーザー名は既に存在しています。";
            }
        }

        return "";
    }

// ユーザー名の重複ここまで


    // 社員名の必須入力チェック
    private static String _validateName(String name) {
        if(name == null || name.equals("")) {
            return "氏名を入力してください。";
        }

        return "";
    }

    // パスワードの必須入力チェック
    private static String _validatePassword(String password, Boolean password_check_flag) {
        // パスワードを変更する場合のみ実行
        if(password_check_flag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }

        return "";
    }
}
