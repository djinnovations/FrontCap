package dj.example.main.model.responses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CSC on 10/26/2018.
 */

public class FrontCapApiResponse {

    public static class FrontCapData{

        public String sectionType;
        public String name;
        public String bannerImage;
        public String firstImage;
        public String secondImage;

        private List<Products> products = new ArrayList<>();

        public List<Products> getProducts() {
            try {
                return new ArrayList<>(products);
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

        public static class Products{

            public String name;
            public String price;
            public String type;
            public String imageURL;
        }
    }

    private List<FrontCapData> content = new ArrayList<>();

    public List<FrontCapData> getContent() {
        try {
            return new ArrayList<>(content);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
