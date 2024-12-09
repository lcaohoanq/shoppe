package com.lcaohoanq.shoppe.util;

import com.lcaohoanq.shoppe.enums.ProductStatus;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public class SampleDataStorage {

    @Getter
    public static class Product {

        String[] genders = {"M", "F", "O", "U"};

        ProductStatus[] koiStatusList = ProductStatus.values();

        List<String>  koiNames = Arrays.asList(
            "Kohaku", "Sanke", "Showa", "Tancho", "Shusui", "Asagi", "Bekko",
            "Utsurimono", "Goshiki", "Kumonryu", "Ochiba", "Koromo", "Yamabuki",
            "Doitsu", "Chagoi", "Ki Utsuri", "Beni Kikokuryu", "Platinum Ogon",
            "Hariwake", "Kikokuryu", "Matsuba", "Ginrin Kohaku", "Ginrin Sanke",
            "Ginrin Showa", "Doitsu Kohaku", "Doitsu Sanke", "Doitsu Showa",
            "Aka Matsuba", "Kage Shiro Utsuri", "Kin Showa", "Orenji Ogon",
            "Kikusui", "Ki Bekko", "Hikari Muji", "Hikari Utsuri", "Benigoi",
            "Soragoi", "Midorigoi", "Ginrin Chagoi", "Kanoko Kohaku",
            "Kanoko Sanke", "Kanoko Showa", "Kujaku", "Doitsu Kujaku",
            "Yamatonishiki", "Budo Sanke", "Ai Goromo", "Sumi Goromo",
            "Kin Ki Utsuri", "Gin Shiro Utsuri"
        );

    }

    //others sample data here
    @Getter
    public static class KoiImage {
        String imageUrl = "https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png";
        String videoUrl = "https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png";
    }

}
