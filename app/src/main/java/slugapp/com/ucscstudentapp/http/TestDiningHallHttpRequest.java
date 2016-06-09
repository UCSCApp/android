package slugapp.com.ucscstudentapp.http;

import android.content.Context;

import org.json.JSONException;

import slugapp.com.ucscstudentapp.interfaces.HttpCallback;
import slugapp.com.ucscstudentapp.models.DiningHall;
import slugapp.com.ucscstudentapp.models.DiningHallWrapper;

/**
 * Created by simba on 8/1/15.
 */
public class TestDiningHallHttpRequest extends DiningHallHttpRequest {
    private final static String normal = "{\"breakfast\":[{\"name\":\"Belgian Waffles\",\"attribs\":[\"eggs\",\"soy\",\"veggie\"]},{\"name\":\"Cage Free Scrambled Eggs\",\"attribs\":[\"eggs\",\"gluten\",\"veggie\"]},{\"name\":\"Eggbeaters Scramble\",\"attribs\":[\"eggs\",\"gluten\",\"veggie\"]},{\"name\":\"Granola Pancakes\",\"attribs\":[\"eggs\",\"milk\",\"nuts\",\"soy\",\"veggie\"]},{\"name\":\"Hard-boiled Cage Free Eggs\",\"attribs\":[\"eggs\",\"gluten\",\"veggie\"]},{\"name\":\"Natural BridgesTofu Scramble\",\"attribs\":[\"soy\",\"vegan\",\"gluten\"]},{\"name\":\"Roasted Yukon Gold Potatoes\",\"attribs\":[\"vegan\",\"gluten\"]},{\"name\":\"Sausage Links\",\"attribs\":[\"pork\",\"gluten\"]},{\"name\":\"Oatmeal Gluten-Free\",\"attribs\":[\"vegan\",\"gluten\"]},{\"name\":\"Blueberry Muffin\",\"attribs\":[\"milk\",\"eggs\",\"soy\",\"veggie\"]},{\"name\":\"Muffin Orange Cranberry\",\"attribs\":[\"eggs\",\"milk\",\"soy\"]},{\"name\":\"Three-Berry Coffee Cake\",\"attribs\":[\"milk\",\"eggs\",\"soy\",\"veggie\"]},{\"name\":\"Vegan Granola\",\"attribs\":[\"nuts\",\"soy\",\"vegan\"]},{\"name\":\"Cage Free Omelette Bar\",\"attribs\":[]}],\"lunch\":[{\"name\":\"Turkey Noodle Soup\",\"attribs\":[\"eggs\"]},{\"name\":\"White Bean with Kale Soup\",\"attribs\":[\"milk\",\"gluten\",\"veggie\"]},{\"name\":\"Beef Gyro\",\"attribs\":[\"milk\",\"soy\",\"beef\"]},{\"name\":\"Porcini Crusted Roasted Chicken\",\"attribs\":[\"milk\"]},{\"name\":\"BBQ Mayzan Wrap\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"BBQ Pork KC Wrap\",\"attribs\":[\"milk\",\"pork\"]},{\"name\":\"Red Skin Mashed Potatoes\",\"attribs\":[\"milk\",\"veggie\",\"gluten\"]},{\"name\":\"Beef Hot Dog\",\"attribs\":[\"soy\",\"beef\",\"pork\"]},{\"name\":\"Cheeseburger\",\"attribs\":[\"milk\",\"soy\",\"beef\"]},{\"name\":\"French Fries\",\"attribs\":[\"vegan\",\"soy\"]},{\"name\":\"Hamburger\",\"attribs\":[\"soy\",\"beef\"]},{\"name\":\"Hot Dog Vegan\",\"attribs\":[\"vegan\",\"soy\"]},{\"name\":\"Mayzan Burger\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"Blueberry Turnovers\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"French Rolls\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"Mint Chip Cookie\",\"attribs\":[\"milk\",\"eggs\",\"veggie\"]},{\"name\":\"Rice Krispy Treats\",\"attribs\":[\"soy\"]},{\"name\":\"Bar Taqueria\",\"attribs\":[]},{\"name\":\"Carne Chicken\",\"attribs\":[]},{\"name\":\"Carne Pork Carnitas\",\"attribs\":[\"pork\"]},{\"name\":\"Condiments\",\"attribs\":[]},{\"name\":\"Corn Tortillas\",\"attribs\":[\"vegan\",\"gluten\"]},{\"name\":\"Flour Tortilla\",\"attribs\":[\"veggie\"]},{\"name\":\"Jardin Vegan Taco Filling\",\"attribs\":[\"vegan\",\"nuts\",\"soy\"]},{\"name\":\"Mexican Rice\",\"attribs\":[\"vegan\",\"gluten\"]},{\"name\":\"Refried Beans\",\"attribs\":[\"vegan\"]}],\"dinner\":[{\"name\":\"Turkey Noodle Soup\",\"attribs\":[\"eggs\"]},{\"name\":\"White Bean with Kale Soup\",\"attribs\":[\"milk\",\"gluten\",\"veggie\"]},{\"name\":\"BBQ Beef Brisket\",\"attribs\":[\"beef\",\"gluten\"]},{\"name\":\"Sizzling Thai Chicken Salad\",\"attribs\":[\"soy\"]},{\"name\":\"Sizzling Thai Seitan Salad\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"Garlic Mashed Yukon Golds\",\"attribs\":[\"milk\",\"veggie\"]},{\"name\":\"Ranch Chili Beans\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"Beef Hot Dog\",\"attribs\":[\"soy\",\"beef\",\"pork\"]},{\"name\":\"Cheeseburger\",\"attribs\":[\"milk\",\"soy\",\"beef\"]},{\"name\":\"French Fries\",\"attribs\":[\"vegan\",\"soy\"]},{\"name\":\"Hamburger\",\"attribs\":[\"soy\",\"beef\"]},{\"name\":\"Hot Dog Vegan\",\"attribs\":[\"vegan\",\"soy\"]},{\"name\":\"Mayzan Burger\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"Blueberry Turnovers\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"French Rolls\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"Red Velvet Cake\",\"attribs\":[\"milk\",\"eggs\",\"soy\",\"veggie\"]},{\"name\":\"Bar Taqueria\",\"attribs\":[]},{\"name\":\"Carne Chicken\",\"attribs\":[]},{\"name\":\"Carne Pork Carnitas\",\"attribs\":[\"pork\"]},{\"name\":\"Condiments\",\"attribs\":[]},{\"name\":\"Corn Tortillas\",\"attribs\":[\"vegan\",\"gluten\"]},{\"name\":\"Flour Tortilla\",\"attribs\":[\"veggie\"]},{\"name\":\"Jardin Vegan Taco Filling\",\"attribs\":[\"vegan\",\"nuts\",\"soy\"]},{\"name\":\"Mexican Rice\",\"attribs\":[\"vegan\",\"gluten\"]},{\"name\":\"Refried Beans\",\"attribs\":[\"vegan\"]}]}";
    private final static String withoutBreakfast = "{\"lunch\":[{\"name\":\"Turkey Noodle Soup\",\"attribs\":[\"eggs\"]},{\"name\":\"White Bean with Kale Soup\",\"attribs\":[\"milk\",\"gluten\",\"veggie\"]},{\"name\":\"Beef Gyro\",\"attribs\":[\"milk\",\"soy\",\"beef\"]},{\"name\":\"Porcini Crusted Roasted Chicken\",\"attribs\":[\"milk\"]},{\"name\":\"BBQ Mayzan Wrap\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"BBQ Pork KC Wrap\",\"attribs\":[\"milk\",\"pork\"]},{\"name\":\"Red Skin Mashed Potatoes\",\"attribs\":[\"milk\",\"veggie\",\"gluten\"]},{\"name\":\"Beef Hot Dog\",\"attribs\":[\"soy\",\"beef\",\"pork\"]},{\"name\":\"Cheeseburger\",\"attribs\":[\"milk\",\"soy\",\"beef\"]},{\"name\":\"French Fries\",\"attribs\":[\"vegan\",\"soy\"]},{\"name\":\"Hamburger\",\"attribs\":[\"soy\",\"beef\"]},{\"name\":\"Hot Dog Vegan\",\"attribs\":[\"vegan\",\"soy\"]},{\"name\":\"Mayzan Burger\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"Blueberry Turnovers\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"French Rolls\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"Mint Chip Cookie\",\"attribs\":[\"milk\",\"eggs\",\"veggie\"]},{\"name\":\"Rice Krispy Treats\",\"attribs\":[\"soy\"]},{\"name\":\"Bar Taqueria\",\"attribs\":[]},{\"name\":\"Carne Chicken\",\"attribs\":[]},{\"name\":\"Carne Pork Carnitas\",\"attribs\":[\"pork\"]},{\"name\":\"Condiments\",\"attribs\":[]},{\"name\":\"Corn Tortillas\",\"attribs\":[\"vegan\",\"gluten\"]},{\"name\":\"Flour Tortilla\",\"attribs\":[\"veggie\"]},{\"name\":\"Jardin Vegan Taco Filling\",\"attribs\":[\"vegan\",\"nuts\",\"soy\"]},{\"name\":\"Mexican Rice\",\"attribs\":[\"vegan\",\"gluten\"]},{\"name\":\"Refried Beans\",\"attribs\":[\"vegan\"]}],\"dinner\":[{\"name\":\"Turkey Noodle Soup\",\"attribs\":[\"eggs\"]},{\"name\":\"White Bean with Kale Soup\",\"attribs\":[\"milk\",\"gluten\",\"veggie\"]},{\"name\":\"BBQ Beef Brisket\",\"attribs\":[\"beef\",\"gluten\"]},{\"name\":\"Sizzling Thai Chicken Salad\",\"attribs\":[\"soy\"]},{\"name\":\"Sizzling Thai Seitan Salad\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"Garlic Mashed Yukon Golds\",\"attribs\":[\"milk\",\"veggie\"]},{\"name\":\"Ranch Chili Beans\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"Beef Hot Dog\",\"attribs\":[\"soy\",\"beef\",\"pork\"]},{\"name\":\"Cheeseburger\",\"attribs\":[\"milk\",\"soy\",\"beef\"]},{\"name\":\"French Fries\",\"attribs\":[\"vegan\",\"soy\"]},{\"name\":\"Hamburger\",\"attribs\":[\"soy\",\"beef\"]},{\"name\":\"Hot Dog Vegan\",\"attribs\":[\"vegan\",\"soy\"]},{\"name\":\"Mayzan Burger\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"Blueberry Turnovers\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"French Rolls\",\"attribs\":[\"soy\",\"vegan\"]},{\"name\":\"Red Velvet Cake\",\"attribs\":[\"milk\",\"eggs\",\"soy\",\"veggie\"]},{\"name\":\"Bar Taqueria\",\"attribs\":[]},{\"name\":\"Carne Chicken\",\"attribs\":[]},{\"name\":\"Carne Pork Carnitas\",\"attribs\":[\"pork\"]},{\"name\":\"Condiments\",\"attribs\":[]},{\"name\":\"Corn Tortillas\",\"attribs\":[\"vegan\",\"gluten\"]},{\"name\":\"Flour Tortilla\",\"attribs\":[\"veggie\"]},{\"name\":\"Jardin Vegan Taco Filling\",\"attribs\":[\"vegan\",\"nuts\",\"soy\"]},{\"name\":\"Mexican Rice\",\"attribs\":[\"vegan\",\"gluten\"]},{\"name\":\"Refried Beans\",\"attribs\":[\"vegan\"]}]}";
    private final static String noMeals = "{}";
    private final static String emptyString = "";

    public TestDiningHallHttpRequest(Context context, String name) {
        super(context, name);
    }

    @Override
    public void execute(HttpCallback<DiningHall> callback) {
        try {
            callback.onSuccess(new DiningHallWrapper(emptyString, name));
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
}
