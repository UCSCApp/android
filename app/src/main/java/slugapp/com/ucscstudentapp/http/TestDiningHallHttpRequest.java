package slugapp.com.ucscstudentapp.http;

import org.json.JSONArray;
import org.json.JSONException;

import slugapp.com.ucscstudentapp.dining.DiningHall;
import slugapp.com.ucscstudentapp.dining.DiningHallWrapper;

/**
 * Created by isayyuhh_s on 9/1/2015.
 */
public class TestDiningHallHttpRequest extends DiningHallHttpRequest {
    private String name;

    public TestDiningHallHttpRequest (String name) {
        this.name = name;
    }

    @Override
    public void execute(HttpCallback<DiningHall> callback) {
            /*
        try {
            JSONArray arr = new JSONArray(json);
            callback.onSuccess(new DiningHallWrapper(arr, name));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        */
    }

    private final static String json = "[\n" +
            "  {\n" +
            "    \"name\": \"Cowell\",\n" +
            "    \"items\": {\n" +
            "      \"breakfast\": [\n" +
            "        {\n" +
            "          \"name\": \"Belgian Waffles\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cage Free Scrambled Eggs\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Eggbeaters Scramble\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hard cooked Cage Free Eggs\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Natural BridgesTofu Scramble\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Banana and Coconut Pancakes\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Oatmeal Gluten-Free\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Sliced Portuguese Linguica\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Spam and Egg Scramble\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Chocolate Loaf Bread\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Morning Glory Blueberry Muffin\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Peach Honey Bran Muffin\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegan Granola\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"nuts\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cage Free Eggs Omelette Bar\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"lunch\": [\n" +
            "        {\n" +
            "          \"name\": \"Polynesian Chicken Soup\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Sweet & Sour Mushroom Soup\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Big Island Chicken Sandwich\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Maui Mahi Mahi\",\n" +
            "          \"attribs\": [\n" +
            "            \"fish\",\n" +
            "            \"nuts\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cowell Specialty Breads\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"nuts\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Chocolate Chip Cookie\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Kalua Mayzan Slider\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"veggie\",\n" +
            "            \"eggs\",\n" +
            "            \"milk\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Kalua Pork Slider\",\n" +
            "          \"attribs\": [\n" +
            "            \"pork\",\n" +
            "            \"soy\",\n" +
            "            \"eggs\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Brownie M&M\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Granola Nut Cookies\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hawaiian Baked Beans\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hawaiian Bar\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hawaiian Coleslaw\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hawaiian Macaroni Salad\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Roasted Sweet Potatoes\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Spam Fried Rice\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"pork\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"dinner\": [\n" +
            "        {\n" +
            "          \"name\": \"Island Beef Steak\",\n" +
            "          \"attribs\": [\n" +
            "            \"beef\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Spicy Hawaiian Mango and Shrimp Stir fry\",\n" +
            "          \"attribs\": [\n" +
            "            \"fish\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Spicy Hawaiian Mango and Tofu Stirfry\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"BBQ Chicken Pizza\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cheese Pizza\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Brownie M&M\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"French Rolls\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Pumpkin Pie\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hawaiian Baked Beans\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hawaiian Bar\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hawaiian Coleslaw\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hawaiian Macaroni Salad\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Roasted Sweet Potatoes\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Spam Fried Rice\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"pork\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Crown\",\n" +
            "    \"items\": {\n" +
            "      \"breakfast\": [\n" +
            "        {\n" +
            "          \"name\": \"Belgian Waffles\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cage Free Scrambled Eggs\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Crispy Bacon\",\n" +
            "          \"attribs\": [\n" +
            "            \"gluten\",\n" +
            "            \"pork\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Eggbeaters Scramble\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hard cooked Cage Free Eggs\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Natural BridgesTofu Scramble\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"French Toast\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Chocolate Loaf Bread\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Morning Glory Blueberry Muffin\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Peach Honey Bran Muffin\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cage Free Eggs Omelette Bar\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"lunch\": [\n" +
            "        {\n" +
            "          \"name\": \"Fire Roasted Corn Soup\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Texas Beef Chili\",\n" +
            "          \"attribs\": [\n" +
            "            \"beef\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"California BLT Wrap\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hot Turkey Sandwich\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegetable Lo Mein\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Chocolate Chip Cookie\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Brownie M&M\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"French Rolls\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Granola Nut Cookies\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"BAR Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"BBQ Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Condiments\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hawaiian Coleslaw\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hot 'N Spicy Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Teriyaki Glaze Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegan Baked Beans\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegan Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"dinner\": [\n" +
            "        {\n" +
            "          \"name\": \"California Pasta\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Italian Herb Roasted Pork Chop\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Roasted Toscano Potatoes\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Brownie M&M\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"French Rolls\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Pumpkin Pie\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"BAR Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"BBQ Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Condiments\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hawaiian Coleslaw\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hot 'N Spicy Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Teriyaki Glaze Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegan Baked Beans\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegan Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Porter\",\n" +
            "    \"items\": {\n" +
            "      \"breakfast\": [\n" +
            "        {\n" +
            "          \"name\": \"Chocolate Loaf Bread\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Morning Glory Blueberry Muffin\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Peach Honey Bran Muffin\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegan Granola\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"nuts\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Apple Pancakes\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cage Free Eggs Omelette Bar\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Oatmeal Gluten-Free\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Belgian Waffles\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cage Free Scrambled Eggs\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Flour Tortilla\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hard cooked Cage Free Eggs\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hash Brown Patty\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Natural BridgesTofu Scramble\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Sausage Links\",\n" +
            "          \"attribs\": [\n" +
            "            \"pork\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"lunch\": [\n" +
            "        {\n" +
            "          \"name\": \"Chocolate Chip Cookie\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Brownie M&M\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Granola Nut Cookies\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Chicken Tortilla Soup\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cream of Carrot & Dill Soup\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"soy\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Portobello Philly Sandwich\",\n" +
            "          \"attribs\": [\n" +
            "            \"veggie\",\n" +
            "            \"milk\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Bacon Cheeseburger Meatloaf\",\n" +
            "          \"attribs\": [\n" +
            "            \"beef\",\n" +
            "            \"pork\",\n" +
            "            \"eggs\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cashew Paella Valencia\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"nuts\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Seafood Cashew Paella Valencia\",\n" +
            "          \"attribs\": [\n" +
            "            \"fish\",\n" +
            "            \"nuts\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Steak Cut Fries\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Bar Pasta\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cheese Ravioli\",\n" +
            "          \"attribs\": [\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Creamy Alfredo Sauce\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Marinara Sauce\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Meatballs\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cheese Pizza\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegetable Pizza\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"dinner\": [\n" +
            "        {\n" +
            "          \"name\": \"French Rolls\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Pumpkin Pie\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Corned Beef Brisket\",\n" +
            "          \"attribs\": [\n" +
            "            \"beef\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Orange Glazed Pork\",\n" +
            "          \"attribs\": [\n" +
            "            \"pork\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Orange Glazed Seitan\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Red Parslied Potatoes\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Steamed Cabbage\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Sticky Rice\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Bar Pasta\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cheese Ravioli\",\n" +
            "          \"attribs\": [\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Creamy Alfredo Sauce\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Marinara Sauce\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Meatballs\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cheese Pizza\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegetable Pizza\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"College Eight\",\n" +
            "    \"items\": {\n" +
            "      \"breakfast\": [\n" +
            "        {\n" +
            "          \"name\": \"Belgian Waffles\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cage Free Scrambled Eggs\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Eggbeaters Scramble\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hard cooked Cage Free Eggs\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Healthy Mondays\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Natural BridgesTofu Scramble\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Oatmeal Toppings Bar\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"nuts\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Roasted Potatoes\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Soyrizo and Potatoes\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Pumpkin Pancakes\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Oatmeal Gluten-Free\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Chocolate Loaf Bread\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Morning Glory Blueberry Muffin\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Peach Honey Bran Muffin\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegan Granola\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"nuts\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cage Free Eggs Omelette Bar\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"lunch\": [\n" +
            "        {\n" +
            "          \"name\": \"Indian Vegetable Soup\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"nuts\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Tomato Basil Bisque\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Chipotle Seitan and Hummus Wrap\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Organic Brown Basmati Rice\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Spicy Garbanzo and Vegetable Stir Fry\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Veggie Stromboli\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Grilled Goat Cheese and Peppedew\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Brownie M&M\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Chocolate Chip Cookie\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"French Rolls\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Granola Nut Cookies\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Baked Potato Bar Condiments\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Baked Potato\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Baked Sweet Potato\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"dinner\": [\n" +
            "        {\n" +
            "          \"name\": \"Healthy Mondays\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Indian Vegetable Soup\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"nuts\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Tomato Basil Bisque\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Asian Spiced Mayzan Meatloaf\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Greek Penne with Spinach & Feta\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"soy\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Quinoa Fried \\\"Rice\\\"\",\n" +
            "          \"attribs\": [\n" +
            "            \"veggie\",\n" +
            "            \"eggs\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegan Veggie Pizza\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Brownie M&M\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"French Rolls\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Pumpkin Pie\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Baked Potato Bar Condiments\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Baked Potato\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Baked Sweet Potato\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"College Nine\",\n" +
            "    \"items\": {\n" +
            "      \"breakfast\": [\n" +
            "        {\n" +
            "          \"name\": \"Chicken Apple Sausage\",\n" +
            "          \"attribs\": [\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Belgian Waffles\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cage Free Scrambled Eggs\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hard cooked Cage Free Eggs\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"gluten\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Natural BridgesTofu Scramble\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Pumpkin Pancakes\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hash Brown Patty\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Oatmeal Gluten-Free\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Chocolate Loaf Bread\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Morning Glory Blueberry Muffin\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Peach Honey Bran Muffin\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegan Granola\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"nuts\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Cage Free Eggs Omelette Bar\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"lunch\": [\n" +
            "        {\n" +
            "          \"name\": \"Harvest Vegetable Soup with Basil\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Portuguese Sausage\\/Bean Soup\",\n" +
            "          \"attribs\": [\n" +
            "            \"pork\",\n" +
            "            \"milk\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Chicken Nuggets\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hoisin Ginger Meatloaf\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"pork\",\n" +
            "            \"eggs\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Panzanella Bread Salad\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Thai Coconut Mayzan Stirfry\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Pizza Veggie Combo\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Asian Sesame Mashed Potatoes\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Brownie M&M\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"French Rolls\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Granola Nut Cookies\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"BAR Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"BBQ Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Condiments\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hawaiian Coleslaw\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Hot 'N Spicy Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Teriyaki Glaze Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegan Baked Beans\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Vegan Wings\",\n" +
            "          \"attribs\": [\n" +
            "            \"vegan\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"dinner\": [\n" +
            "        {\n" +
            "          \"name\": \"Oven Roasted Turkey\",\n" +
            "          \"attribs\": [\n" +
            "            \"gluten\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Shrimp and Scallop Diablo on Linguine\",\n" +
            "          \"attribs\": [\n" +
            "            \"fish\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Tofu Diablo on Linguine\",\n" +
            "          \"attribs\": [\n" +
            "            \"soy\",\n" +
            "            \"vegan\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Sweet & Spicy Couscous\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Pizza Veggie Combo\",\n" +
            "          \"attribs\": [\n" +
            "            \n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Traditional Sage Stuffing\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"soy\",\n" +
            "            \"veggie\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Brownie M&M\",\n" +
            "          \"attribs\": [\n" +
            "            \"eggs\",\n" +
            "            \"milk\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"Pumpkin Pie\",\n" +
            "          \"attribs\": [\n" +
            "            \"milk\",\n" +
            "            \"eggs\",\n" +
            "            \"soy\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "]\n";
}
