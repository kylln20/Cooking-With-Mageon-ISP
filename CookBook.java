/**
 * A program to store all possible recipes
 *
 * Course Info:
 * ICS4UO
 * Valentina Krasteva
 *
 * @author Angelina Jiang
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CookBook {
    /** The arraylist of recipes, to store the recipes */
    ArrayList<Recipes> cookbook;

    private Food lettuce;
    private Food bread;
    private Food healthyBread;
    private Food milk;
    private Food cookedRice;
    private Food healthyCookedRice;
    private Food tomato;
    private Food potato;
    private Food carrot;
    private Food eggs;
    private Food beef;
    private Food fish;
    private Food apple;
    private Food spinach;
    private Food butter;
    private Food corn;
    private Food cheese;

    /**
     * CookBook constructor
     *
     * Creates all the recipes possible and adds them to the cookbook variable
     */
    public CookBook() {
        cookbook = new ArrayList<>();
        // initializing all the possible ingredients
        lettuce = new Food("Lettuce", "Pictures/lettuce.png", new int[]{0, 2}, 30, "im", 1.97);
        bread = new Food("Bread", "Pictures/flour(white).png", new int[]{1, 5}, 302, "im", 2.60);
        healthyBread = new Food("Healthy Bread", "Pictures/flour(multigrain).png", new int[]{6, 7}, 160, "im", 2.99);
        milk = new Food("Milk", "Pictures/milk.png", new int[]{0, 3, 8}, 150, "im", 1.47);
        cookedRice = new Food("Cooked Rice", "Pictures/rice(white).png", new int[]{3, 8, 9}, 242, "im", 2.87);
        healthyCookedRice = new Food("Healthy Cooked Rice", "Pictures/rice(multigrain).png", new int[]{3, 7, 8, 9}, 180, "im", 3.47);
        tomato = new Food("Tomato", "Pictures/tomato.png", new int[]{2}, 22, "im", 1.25);
        potato = new Food("Cooked Potato", "Pictures/potato.png", new int[]{2, 10}, 87, "im", 1.84);
        carrot = new Food("Carrot", "Pictures/carrot.png", new int[]{0, 2}, 22, "im", 1.97);
        eggs = new Food("Cooked Eggs", "Pictures/eggs.png", new int[]{11, 3, 12}, 78, "im", 1.31);
        beef = new Food("Cooked Beef", "Pictures/beef.png", new int[]{1, 5}, 217, "im", 1.25);
        fish = new Food("Cooked Fish", "Pictures/fish.png", new int[]{1, 3, 12, 13}, 190, "im", 2.50);
        apple = new Food("Apple", "Pictures/apple.png", new int[]{2, 9}, 95, "im", 0.79);
        spinach = new Food("Spinach", "Pictures/leafy_greens.png", new int[]{4, 0, 12, 7}, 30, "im", 1.80);
        butter = new Food("Butter", "Pictures/butter.png", new int[]{10, 3, 11}, 102, "im", 1.99);
        corn = new Food("Cooked Corn", "Pictures/corn.png", new int[]{1, 2, 7}, 177, "im", 2.04);
        cheese = new Food("Cheese", "Pictures/cheese.png", new int[]{13, 0, 10, 8}, 100, "im", 1.77);

        ArrayList<Food> sa = new ArrayList<>(); // making salad
        sa.add(lettuce);
        sa.add(spinach);
        Recipes salad = new Recipes("Salad", sa);
        cookbook.add(salad);

        ArrayList<Food> tomatoSalad = new ArrayList<>(); // making tomato salad
        tomatoSalad.add(tomato);
        tomatoSalad.add(cheese);
        tomatoSalad.add(spinach);
        Recipes ts = new Recipes("Tomato Salad", tomatoSalad);
        cookbook.add(ts);

        ArrayList<Food> tsw = new ArrayList<>(); // making tomato salad with carrots
        tsw.add(tomato);
        tsw.add(cheese);
        tsw.add(spinach);
        tsw.add(carrot);
        Recipes tswc = new Recipes("Tomato Salad with Carrots", tsw);
        cookbook.add(tswc);

        ArrayList<Food> applePie = new ArrayList<>();
        applePie.add(apple);
        applePie.add(bread);
        applePie.add(butter);
        Recipes applePieRec = new Recipes("Apple Pie", applePie);
        cookbook.add(applePieRec);

        ArrayList<Food> happlePie = new ArrayList<>();
        happlePie.add(apple);
        happlePie.add(bread);
        happlePie.add(butter);
        Recipes happlePieRec = new Recipes("Healthy Apple Pie", happlePie);
        cookbook.add(happlePieRec);

        ArrayList<Food> fishPie = new ArrayList<>();
        fishPie.add(apple);
        fishPie.add(bread);
        fishPie.add(butter);
        Recipes fishPierec = new Recipes("Fish Pie", fishPie);
        cookbook.add(fishPierec);

        ArrayList<Food> hfishPie = new ArrayList<>();
        hfishPie.add(apple);
        hfishPie.add(healthyBread);
        hfishPie.add(butter);
        Recipes hfishPierec = new Recipes("Fish Pie", hfishPie);
        cookbook.add(hfishPierec);

        ArrayList<Food> fishPiewc = new ArrayList<>();
        fishPiewc.add(apple);
        fishPiewc.add(bread);
        fishPiewc.add(butter);
        fishPiewc.add(corn);
        Recipes fishPierecwc = new Recipes("Fish Pie", fishPiewc);
        cookbook.add(fishPierecwc);

        ArrayList<Food> hfishPiewc = new ArrayList<>();
        hfishPiewc.add(apple);
        hfishPiewc.add(healthyBread);
        hfishPiewc.add(butter);
        hfishPiewc.add(corn);
        Recipes hfishPiewcrec = new Recipes("Fish Pie", hfishPiewc);
        cookbook.add(hfishPiewcrec);

        ArrayList<Food> potPie = new ArrayList<>();
        potPie.add(butter);
        potPie.add(carrot);
        potPie.add(bread);
        potPie.add(eggs);
        Recipes pp = new Recipes("Pot Pie", potPie);
        cookbook.add(pp);

        ArrayList<Food> hpotPie = new ArrayList<>();
        hpotPie.add(butter);
        hpotPie.add(carrot);
        hpotPie.add(healthyBread);
        hpotPie.add(eggs);
        Recipes hpp = new Recipes("Pot Pie", hpotPie);
        cookbook.add(hpp);

        ArrayList<Food> rvsfwl = new ArrayList<>();
        rvsfwl.add(cookedRice);
        rvsfwl.add(spinach);
        rvsfwl.add(lettuce);
        Recipes rvsfw = new Recipes("Rice and Vegetable Stir-Fry with Lettuce", rvsfwl);
        cookbook.add(rvsfw);

        ArrayList<Food> hrvsfwl = new ArrayList<>();
        hrvsfwl.add(healthyCookedRice);
        hrvsfwl.add(spinach);
        hrvsfwl.add(lettuce);
        Recipes hrvsfw = new Recipes("Healthy Rice and Vegetable Stir-Fry with Lettuce", hrvsfwl);
        cookbook.add(hrvsfw);

        ArrayList<Food> rvsf = new ArrayList<>();
        rvsf.add(cookedRice);
        rvsf.add(spinach);
        Recipes rrvsf = new Recipes("Healthy Rice and Vegetable Stir-Fry", rvsf);
        cookbook.add(rrvsf);

        ArrayList<Food> hrvsf = new ArrayList<>();
        hrvsf.add(healthyCookedRice);
        hrvsf.add(spinach);
        Recipes hrrvsf = new Recipes("Healthy Rice and Vegetable Stir-Fry", hrvsf);
        cookbook.add(hrrvsf);

        ArrayList<Food> hbvsf = new ArrayList<>();
        hbvsf.add(beef);
        hbvsf.add(spinach);
        hbvsf.add(lettuce);
        Recipes hbbvsf = new Recipes("Beef and Vegetable Stir-Fry with Lettuce", hbvsf);
        cookbook.add(hbbvsf);

        ArrayList<Food> bvsf = new ArrayList<>();
        bvsf.add(beef);
        bvsf.add(spinach);
        bvsf.add(lettuce);
        Recipes bbvsf = new Recipes("Beef and Vegetable Stir-Fry", bvsf);
        cookbook.add(bbvsf);

        ArrayList<Food> ssl = new ArrayList<>();
        ssl.add(fish);
        ssl.add(spinach);
        ssl.add(lettuce);
        Recipes swsal = new Recipes("Salmon with Spinach and Lettuce", ssl);
        cookbook.add(swsal);

        ArrayList<Food> sl = new ArrayList<>();
        sl.add(fish);
        sl.add(lettuce);
        Recipes swl = new Recipes("Salmon with Lettuce", sl);
        cookbook.add(swl);

        ArrayList<Food> ss = new ArrayList<>();
        ss.add(fish);
        ss.add(spinach);
        Recipes sws = new Recipes("Salmon with Spinach", ss);
        cookbook.add(sws);

        ArrayList<Food> pizza = new ArrayList<>();
        pizza.add(bread);
        pizza.add(tomato);
        pizza.add(cheese);
        Recipes piz = new Recipes("Pizza", pizza);
        cookbook.add(piz);

        ArrayList<Food> hpizza = new ArrayList<>();
        hpizza.add(healthyBread);
        hpizza.add(tomato);
        hpizza.add(cheese);
        Recipes hpiz = new Recipes("Healthy Pizza", hpizza);
        cookbook.add(hpiz);

        ArrayList<Food> cps = new ArrayList<>();
        cps.add(carrot);
        cps.add(potato);
        cps.add(butter);
        Recipes rcps = new Recipes("Carrot Potato Soup", cps);
        cookbook.add(rcps);

        ArrayList<Food> cpsws = new ArrayList<>();
        cpsws.add(carrot);
        cpsws.add(potato);
        cpsws.add(butter);
        cpsws.add(spinach);
        Recipes rcpsws = new Recipes("Carrot Potato Soup with Spinach", cpsws);
        cookbook.add(rcpsws);

        ArrayList<Food> omelette = new ArrayList<>();
        omelette.add(eggs);
        omelette.add(butter);
        omelette.add(cheese);
        Recipes om = new Recipes("Omelette", omelette);
        cookbook.add(om);

        ArrayList<Food> omelettews = new ArrayList<>();
        omelettews.add(eggs);
        omelettews.add(butter);
        omelettews.add(cheese);
        omelettews.add(spinach);
        Recipes omws = new Recipes("Omelette with Spinach", omelettews);
        cookbook.add(omws);

        ArrayList<Food> pan = new ArrayList<>();
        pan.add(bread);
        pan.add(eggs);
        pan.add(milk);
        Recipes pancake = new Recipes("Pancakes", pan);
        cookbook.add(pancake);

        ArrayList<Food> hpan = new ArrayList<>();
        hpan.add(bread);
        hpan.add(eggs);
        hpan.add(milk);
        Recipes hpancake = new Recipes("Healthy Pancakes", hpan);
        cookbook.add(hpancake);

        ArrayList<Food> tpan = new ArrayList<>();
        tpan.add(bread);
        tpan.add(eggs);
        tpan.add(milk);
        tpan.add(butter);
        Recipes tpancakes = new Recipes("Tasty Pancakes", tpan);
        cookbook.add(tpancakes);

        ArrayList<Food> htpan = new ArrayList<>();
        htpan.add(bread);
        htpan.add(eggs);
        htpan.add(milk);
        htpan.add(butter);
        Recipes htpancakes = new Recipes("Healthy Tasty Pancakes", htpan);
        cookbook.add(htpancakes);

        ArrayList<Food> bisc = new ArrayList<>();
        bisc.add(bread);
        bisc.add(butter);
        bisc.add(milk);
        Recipes biscuits = new Recipes("Biscuits", bisc);
        cookbook.add(biscuits);

        ArrayList<Food> hbisc = new ArrayList<>();
        hbisc.add(healthyBread);
        hbisc.add(butter);
        hbisc.add(milk);
        Recipes hbiscuits = new Recipes("Healthy Biscuits", hbisc);
        cookbook.add(hbiscuits);

        ArrayList<Food> hb = new ArrayList<>();
        hb.add(potato);
        hb.add(bread);
        hb.add(eggs);
        Recipes hashBrown = new Recipes("Hash Browns", hb);
        cookbook.add(hashBrown);

        ArrayList<Food> hhb = new ArrayList<>();
        hhb.add(potato);
        hhb.add(bread);
        hhb.add(eggs);
        Recipes hhashBrown = new Recipes("Healthy Hash Browns", hhb);
        cookbook.add(hhashBrown);

        ArrayList<Food> hbws = new ArrayList<>();
        hbws.add(potato);
        hbws.add(bread);
        hbws.add(eggs);
        Recipes hashBrownws = new Recipes("Healthy Hash Browns with Spinach", hbws);
        cookbook.add(hashBrownws);

        ArrayList<Food> hhbws = new ArrayList<>();
        hhbws.add(potato);
        hhbws.add(bread);
        hhbws.add(eggs);
        Recipes hhashBrownws = new Recipes("Healthy Hash Browns", hhbws);
        cookbook.add(hhashBrownws);

        ArrayList<Food> bur = new ArrayList<>();
        bur.add(beef);
        bur.add(cookedRice);
        bur.add(cheese);
        bur.add(bread);
        Recipes burr = new Recipes("Burritos", bur);
        cookbook.add(burr);

        ArrayList<Food> hbur = new ArrayList<>();
        hbur.add(beef);
        hbur.add(cookedRice);
        hbur.add(cheese);
        hbur.add(healthyBread);
        Recipes hburr = new Recipes("Kind of Healthy Burritos", hbur);
        cookbook.add(hburr);

        ArrayList<Food> hhbur = new ArrayList<>();
        hhbur.add(beef);
        hhbur.add(healthyCookedRice);
        hhbur.add(cheese);
        hhbur.add(bread);
        Recipes hhburr = new Recipes("Healthy Burritos", hhbur);
        cookbook.add(hhburr);

        ArrayList<Food> hhhbur = new ArrayList<>();
        hhhbur.add(beef);
        hhhbur.add(healthyCookedRice);
        hhhbur.add(cheese);
        hhhbur.add(healthyBread);
        Recipes hhhburr = new Recipes("VERY Healthy Burritos", hhhbur);
        cookbook.add(hhhburr);
        // I AM NOT DOING BURRITOES WITH LETTUCE AND TOMATO. NO. JUST. NO.
        ArrayList<Food> gb = new ArrayList<>();
        gb.add(beef);
        gb.add(potato);
        Recipes groundBeef = new Recipes("Ground Beef", gb);
        cookbook.add(groundBeef);

        ArrayList<Food> gbws = new ArrayList<>();
        gbws.add(beef);
        gbws.add(potato);
        gbws.add(spinach);
        Recipes groundBeefws = new Recipes("Ground Beef with Spinach", gbws);
        cookbook.add(groundBeefws);

        ArrayList<Food> cookie = new ArrayList<>();
        cookie.add(butter);
        cookie.add(eggs);
        cookie.add(bread);
        Recipes cookr = new Recipes("Cookies", cookie);
        cookbook.add(cookr);

        ArrayList<Food> hcookie = new ArrayList<>();
        hcookie.add(butter);
        hcookie.add(eggs);
        hcookie.add(healthyBread);
        Recipes hcookr = new Recipes("Healthy Cookies", hcookie);
        cookbook.add(hcookr);

        ArrayList<Food> applecb = new ArrayList<>();
        applecb.add(bread);
        applecb.add(eggs);
        applecb.add(milk);
        applecb.add(butter);
        applecb.add(apple);
        applecb.add(corn);
        Recipes appleCornBread = new Recipes("Apple Cornbread", applecb);
        cookbook.add(appleCornBread);

        ArrayList<Food> happlecb = new ArrayList<>();
        happlecb.add(healthyBread);
        happlecb.add(eggs);
        happlecb.add(milk);
        happlecb.add(butter);
        happlecb.add(apple);
        happlecb.add(corn);
        Recipes happleCornBread = new Recipes("Healthy Apple Cornbread", happlecb);
        cookbook.add(happleCornBread);

        ArrayList<Food> fc = new ArrayList<>();
        fc.add(fish);
        fc.add(bread);
        fc.add(cheese);
        fc.add(potato); // I wanna die wtaf is this shit
        Recipes fishCasserole = new Recipes("Fish Casserole", fc);
        cookbook.add(fishCasserole);

        ArrayList<Food> hfc = new ArrayList<>();
        hfc.add(fish);
        hfc.add(healthyBread);
        hfc.add(cheese);
        hfc.add(potato); // I wanna die wtaf is this shit
        Recipes hfishCasserole = new Recipes("Healthy Fish Casserole", hfc);
        cookbook.add(hfishCasserole);

        ArrayList<Food> bc = new ArrayList<>();
        bc.add(beef);
        bc.add(potato);
        bc.add(cheese);
        bc.add(bread);
        Recipes beefCasserole = new Recipes("Meat Casserole", bc);
        cookbook.add(beefCasserole);

        ArrayList<Food> gcs = new ArrayList<>();
        gcs.add(bread);
        gcs.add(cheese);
        Recipes grilledCheeseSandwich = new Recipes("Grilled Cheese Sandwich", gcs);
        cookbook.add(grilledCheeseSandwich);

        ArrayList<Food> gcswb = new ArrayList<>();
        gcswb.add(bread);
        gcswb.add(cheese);
        gcswb.add(butter);
        Recipes grilledCheeseSandwichwb = new Recipes("Grilled Cheese Sandwich with Butter", gcswb);
        cookbook.add(grilledCheeseSandwichwb);

        ArrayList<Food> hgcswb = new ArrayList<>();
        hgcswb.add(healthyBread);
        hgcswb.add(cheese);
        hgcswb.add(butter);
        Recipes hgrilledCheeseSandwichwb = new Recipes("Healthy Grilled Cheese Sandwich with Butter", hgcswb);
        cookbook.add(hgrilledCheeseSandwichwb);

        ArrayList<Food> hgcs = new ArrayList<>();
        hgcs.add(healthyBread);
        hgcs.add(cheese);
        Recipes hgrilledCheeseSandwich = new Recipes("Grilled Cheese Sandwich", hgcs);
        cookbook.add(hgrilledCheeseSandwich);

        ArrayList<Food> bs = new ArrayList<>();
        bs.add(bread);
        bs.add(beef);
        Recipes beefSandwich = new Recipes("Beef Burger", bs);
        cookbook.add(beefSandwich);
        ArrayList<Food> hbs = new ArrayList<>();
        hbs.add(healthyBread);
        hbs.add(beef);
        Recipes hbeefSandwich = new Recipes("Healthy Beef Burger", hbs);
        cookbook.add(hbeefSandwich);

        ArrayList<Food> cs = new ArrayList<>();
        cs.add(bread);
        cs.add(beef);
        cs.add(cheese);
        Recipes cheeseSandwich = new Recipes("Cheese Burger", cs);
        cookbook.add(cheeseSandwich);
        ArrayList<Food> hcs = new ArrayList<>();
        hcs.add(healthyBread);
        hcs.add(beef);
        hcs.add(cheese);
        Recipes hcheeseBurger = new Recipes("Healthy Cheese Burger", hcs);
        cookbook.add(hcheeseBurger);

        ArrayList<Food> s = new ArrayList<>();
        s.add(bread);
        s.add(beef);
        s.add(cheese);
        s.add(tomato);
        s.add(lettuce);
        Recipes sandwich = new Recipes("Sandwich", s);
        cookbook.add(sandwich);
        ArrayList<Food> hs = new ArrayList<>();
        hs.add(healthyBread);
        hs.add(beef);
        hs.add(cheese);
        hs.add(tomato);
        hs.add(lettuce);
        Recipes hsandwich = new Recipes("Healthy Sandwich", hs);
        cookbook.add(hsandwich);
    }
}
