 package ru.biis.biissale;

import ru.biis.biissale.rest.UserInfo;

public class Messagesbiis {

    private String id;
    private String parentId;
    private String dateTimeOffset;
    private String content;//сообщение
    private String appUserId;
    private String userRequestId;
    private String sellerAvatar;
    private String userProfileAvatar;
   // private String appUserSellerProfile;

    private String userP;
    public Messagesbiis(String id, String parentId, String dateTimeOffset, String content, String appUserId, String userRequestId,String sellerAvatar,String userProfileAvatar/*,String appUserSellerProfil*/) {
        this.id = id;
        this.parentId = parentId;
        this.dateTimeOffset = dateTimeOffset;
        this.content = content;
        this.appUserId = appUserId;
        this.userRequestId = userRequestId;
        this.sellerAvatar = sellerAvatar;
        this.userProfileAvatar = userProfileAvatar;
        //this.appUserSellerProfil = appUserSellerProfil;
    }
    public  String getAppUserId() {
        return  appUserId;
    }
    public String getLogin() {
        UserInfo UserInfo = null;
        String ui= UserInfo.getUserLogin(appUserId);

        return ui;
    }

    public String getDate() {
        return dateTimeOffset;
    }

    public String getMessage() {
        return content;
    }

    public static String checkAva(String av) {//написать метод получения аватарки



        String http = new String("http://");
        String https = new String("https://");
        String biisru= new String("bi-is.ru");
        String png= new String(".png");
        int index1 = av.indexOf(http);
        int index2 = av.indexOf(https);
        int index3 = av.indexOf(biisru);
        int index4 = av.indexOf(png);
        if ((index1 == -1) && (index2 == -1))
        {
            if (index3 == -1)
            {
                return https+biisru+av;//http://bi-is.ru/uploads/avatar/avatar.png
            }
            else
                {
                    return https+av;
                }
        }
        else
        {
            if (index4 == -1){
                return "https://sun9-5.userapi.com/VIe1t6glyxeeuFRZ4nYTcAqBAYTVhEuldagqeg/h4r3Qk8lvoU.jpg?ava=1";}
            else {
            return av;}
        }
        //return "https://sun9-5.userapi.com/VIe1t6glyxeeuFRZ4nYTcAqBAYTVhEuldagqeg/h4r3Qk8lvoU.jpg?ava=1";
    }

    //public String getPrice() {
    //    return price;
    //}

    //   public String getStock() {
    //       return stock;
    //   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Messagesbiis messagesbiis = (Messagesbiis) o;

        if (!id.equals(messagesbiis.id)) return false;
        if (!appUserId.equals(messagesbiis.appUserId)) return false;
        if (!dateTimeOffset.equals(messagesbiis.dateTimeOffset)) return false;
        if (!content.equals(messagesbiis.content)) return false;
        return id != null ? id.equals(messagesbiis.id) : messagesbiis.id == null;
    }
}
