package saizeria;

public class Menu {
	// 金額
	private int price;
	// 商品名
	private String name;
	// 食べ物、飲み物、お酒フラグ
	private int meal;
	private int drink;
	private int osake;
	// 商品コード
	private String code;

	public Menu(String name, int price, int meal, int drink, int osake, String code) {

		this.name = name;
		this.price = price;
		this.meal = meal;
		this.drink = drink;
		this.osake = osake;
		this.code = code;

	}

	// --------------------げったん・せったん--------------------
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMeal() {
		return meal;
	}

	public void setMeal(int meal) {
		this.meal = meal;
	}

	public int getDrink() {
		return drink;
	}

	public void setDrink(int drink) {
		this.drink = drink;
	}

	public int getOsake() {
		return osake;
	}

	public void setOsake(int osake) {
		this.osake = osake;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
