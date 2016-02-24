package com.dean.googleplay.domain;

import com.dean.googleplay.http.GsonParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/17.
 */

@HttpResponse(parser = GsonParser.class)
public class AppList extends ArrayList<AppList.AppInfo> {
    @HttpResponse(parser = GsonParser.class)
    public static class AppInfo {
        /**
         * id : 1525490
         * name : 有缘网
         * packageName : com.youyuan.yyhl
         * iconUrl : app/com.youyuan.yyhl/icon.jpg
         * stars : 4.0
         * downloadNum : 200万+
         * version : 4.1.9
         * date : 2014-04-24
         * size : 3876203
         * downloadUrl : app/com.youyuan.yyhl/com.youyuan.yyhl.apk
         * des : 产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松、易用的大众婚恋交友服务，帮助中国最广泛的单身男女找到理想对象。目前已有1亿3632万单身男女在有缘网上找对象。免费下载，立即注册，更拥有同城交友、自定义搜索、异性推荐等贴心、便捷、高效的婚恋交友服务！找对象，上有缘网！人多机会多，还怕找不到？
         * author : 有缘网
         * screen : ["app/com.youyuan.yyhl/screen0.jpg","app/com.youyuan.yyhl/screen1.jpg","app/com.youyuan.yyhl/screen2.jpg","app/com.youyuan.yyhl/screen3.jpg","app/com.youyuan.yyhl/screen4.jpg"]
         * safe : [{"safeUrl":"app/com.youyuan.yyhl/safeIcon0.jpg","safeDesUrl":"app/com.youyuan.yyhl/safeDesUrl0.jpg","safeDes":"已通过安智市场官方认证，是正版软件","safeDesColor":0},{"safeUrl":"app/com.youyuan.yyhl/safeIcon1.jpg","safeDesUrl":"app/com.youyuan.yyhl/safeDesUrl1.jpg","safeDes":"已通过安智市场安全检测，请放心使用","safeDesColor":0},{"safeUrl":"app/com.youyuan.yyhl/safeIcon2.jpg","safeDesUrl":"app/com.youyuan.yyhl/safeDesUrl2.jpg","safeDes":"无任何形式的广告","safeDesColor":0}]
         */

        private int id;
        private String name;
        private String packageName;
        private String iconUrl;
        private float stars;
        private String downloadNum;
        private String version;
        private String date;
        private int size;
        private String downloadUrl;
        private String des;
        private String author;
        private List<String> screen;
        /**
         * safeUrl : app/com.youyuan.yyhl/safeIcon0.jpg
         * safeDesUrl : app/com.youyuan.yyhl/safeDesUrl0.jpg
         * safeDes : 已通过安智市场官方认证，是正版软件
         * safeDesColor : 0
         */

        private List<SafeEntity> safe;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public void setStars(float stars) {
            this.stars = stars;
        }

        public void setDownloadNum(String downloadNum) {
            this.downloadNum = downloadNum;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setScreen(List<String> screen) {
            this.screen = screen;
        }

        public void setSafe(List<SafeEntity> safe) {
            this.safe = safe;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public float getStars() {
            return stars;
        }

        public String getDownloadNum() {
            return downloadNum;
        }

        public String getVersion() {
            return version;
        }

        public String getDate() {
            return date;
        }

        public int getSize() {
            return size;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public String getDes() {
            return des;
        }

        public String getAuthor() {
            return author;
        }

        public List<String> getScreen() {
            return screen;
        }

        public List<SafeEntity> getSafe() {
            return safe;
        }

        @Override
        public String toString() {
            return "AppInfo{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        public static class SafeEntity {
            private String safeUrl;
            private String safeDesUrl;
            private String safeDes;
            private int safeDesColor;

            public void setSafeUrl(String safeUrl) {
                this.safeUrl = safeUrl;
            }

            public void setSafeDesUrl(String safeDesUrl) {
                this.safeDesUrl = safeDesUrl;
            }

            public void setSafeDes(String safeDes) {
                this.safeDes = safeDes;
            }

            public void setSafeDesColor(int safeDesColor) {
                this.safeDesColor = safeDesColor;
            }

            public String getSafeUrl() {
                return safeUrl;
            }

            public String getSafeDesUrl() {
                return safeDesUrl;
            }

            public String getSafeDes() {
                return safeDes;
            }

            public int getSafeDesColor() {
                return safeDesColor;
            }
        }

        //        private long id;//app的id
//        private String name;//app的软件名称
//        private String packageName;//app的包名
//        private String iconUrl;//app的icon地址
//        private float stars;//app的评价星级
//        private String downloadNum;//app的下载数量
//        private String version;//app的版本
//        private String date;//app的发布日期
//        private long size;//app的size
//        private String downloadUrl;//下载地址
//        private String des;//简介
//        private String author; //作者
//        private List<String> screen;//截图下载地址
//        private List<String> safeUrl;//安全信息图片地址
//        private List<String> safeDesUrl;//安全信息图片勾勾地址
//        private List<String> safeDes;//安全信息图片勾勾后面描述信息
//        private List<Integer> safeDesColor;//安全信息的文字颜色
//
//
//        @Override
//        public String toString() {
//            return "AppInfo{" +
//                    "id=" + id +
//                    ", name='" + name + '\'' +
//                    ", packageName='" + packageName + '\'' +
//                    ", iconUrl='" + iconUrl + '\'' +
//                    ", stars=" + stars +
//                    ", downloadNum='" + downloadNum + '\'' +
//                    ", version='" + version + '\'' +
//                    ", date='" + date + '\'' +
//                    ", size=" + size +
//                    ", downloadUrl='" + downloadUrl + '\'' +
//                    ", des='" + des + '\'' +
//                    ", author='" + author + '\'' +
//                    ", screen=" + screen +
//                    ", safeUrl=" + safeUrl +
//                    ", safeDesUrl=" + safeDesUrl +
//                    ", safeDes=" + safeDes +
//                    ", safeDesColor=" + safeDesColor +
//                    '}';
//        }
//
//        public long getId() {
//            return id;
//        }
//
//        public void setId(long id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getPackageName() {
//            return packageName;
//        }
//
//        public void setPackageName(String packageName) {
//            this.packageName = packageName;
//        }
//
//        public String getIconUrl() {
//            return iconUrl;
//        }
//
//        public void setIconUrl(String iconUrl) {
//            this.iconUrl = iconUrl;
//        }
//
//        public float getStars() {
//            return stars;
//        }
//
//        public void setStars(float stars) {
//            this.stars = stars;
//        }
//
//        public String getDownloadNum() {
//            return downloadNum;
//        }
//
//        public void setDownloadNum(String downloadNum) {
//            this.downloadNum = downloadNum;
//        }
//
//        public String getVersion() {
//            return version;
//        }
//
//        public void setVersion(String version) {
//            this.version = version;
//        }
//
//        public String getDate() {
//            return date;
//        }
//
//        public void setDate(String date) {
//            this.date = date;
//        }
//
//        public long getSize() {
//            return size;
//        }
//
//        public void setSize(long size) {
//            this.size = size;
//        }
//
//        public String getDownloadUrl() {
//            return downloadUrl;
//        }
//
//        public void setDownloadUrl(String downloadUrl) {
//            this.downloadUrl = downloadUrl;
//        }
//
//        public String getDes() {
//            return des;
//        }
//
//        public void setDes(String des) {
//            this.des = des;
//        }
//
//        public String getAuthor() {
//            return author;
//        }
//
//        public void setAuthor(String author) {
//            this.author = author;
//        }
//
//        public List<String> getScreen() {
//            return screen;
//        }
//
//        public void setScreen(List<String> screen) {
//            this.screen = screen;
//        }
//
//        public List<String> getSafeUrl() {
//            return safeUrl;
//        }
//
//        public void setSafeUrl(List<String> safeUrl) {
//            this.safeUrl = safeUrl;
//        }
//
//        public List<String> getSafeDesUrl() {
//            return safeDesUrl;
//        }
//
//        public void setSafeDesUrl(List<String> safeDesUrl) {
//            this.safeDesUrl = safeDesUrl;
//        }
//
//        public List<String> getSafeDes() {
//            return safeDes;
//        }
//
//        public void setSafeDes(List<String> safeDes) {
//            this.safeDes = safeDes;
//        }
//
//        public List<Integer> getSafeDesColor() {
//            return safeDesColor;
//        }
//
//        public void setSafeDesColor(List<Integer> safeDesColor) {
//            this.safeDesColor = safeDesColor;
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeLong(this.id);
//            dest.writeString(this.name);
//            dest.writeString(this.packageName);
//            dest.writeString(this.iconUrl);
//            dest.writeFloat(this.stars);
//            dest.writeString(this.downloadNum);
//            dest.writeString(this.version);
//            dest.writeString(this.date);
//            dest.writeLong(this.size);
//            dest.writeString(this.downloadUrl);
//            dest.writeString(this.des);
//            dest.writeString(this.author);
//            dest.writeStringList(this.screen);
//            dest.writeStringList(this.safeUrl);
//            dest.writeStringList(this.safeDesUrl);
//            dest.writeStringList(this.safeDes);
//            dest.writeList(this.safeDesColor);
//        }
//
//        public AppInfo() {
//        }
//
//        protected AppInfo(Parcel in) {
//            this.id = in.readLong();
//            this.name = in.readString();
//            this.packageName = in.readString();
//            this.iconUrl = in.readString();
//            this.stars = in.readFloat();
//            this.downloadNum = in.readString();
//            this.version = in.readString();
//            this.date = in.readString();
//            this.size = in.readLong();
//            this.downloadUrl = in.readString();
//            this.des = in.readString();
//            this.author = in.readString();
//            this.screen = in.createStringArrayList();
//            this.safeUrl = in.createStringArrayList();
//            this.safeDesUrl = in.createStringArrayList();
//            this.safeDes = in.createStringArrayList();
//            this.safeDesColor = new ArrayList<Integer>();
//            in.readList(this.safeDesColor, List.class.getClassLoader());
//        }
//
//        public static final Parcelable.Creator<AppInfo> CREATOR = new Parcelable.Creator<AppInfo>() {
//            public AppInfo createFromParcel(Parcel source) {
//                return new AppInfo(source);
//            }
//
//            public AppInfo[] newArray(int size) {
//                return new AppInfo[size];
//            }
//        };
    }
}
