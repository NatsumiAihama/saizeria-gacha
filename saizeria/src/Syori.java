package saizeria;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class Syori {

	// --------------------商品リスト読み込み--------------------
	public static List<Menu> readMenu() throws IOException {

		// ---------------------------------------
		// CSVファイルが入るリストを用意する
		List<Menu> csvDataList = new ArrayList<Menu>();

		String file;
		String[] data;

		// ファイルを読み込む
		FileReader fr = new FileReader("saizeria/menu.csv");
		BufferedReader br = new BufferedReader(fr);

		// 円をつけるためにDecimalFormatを準備
		DecimalFormat df = new DecimalFormat("#円");

		// ファイルの中身がなくなるまで繰り返す
		while ((file = br.readLine()) != null) {

			data = file.split(",");

			// 金額をリストへ追加するためにintへ
			int valuePrice = Integer.parseInt(data[1]);
			int valueMeal = Integer.parseInt(data[2]);
			int valueDrink = Integer.parseInt(data[3]);
			int valueOsake = Integer.parseInt(data[4]);

			// それぞれの値や文字列を代入
			String name = data[0];
			int price = valuePrice;
			int meal = valueMeal;
			int drink = valueDrink;
			int osake = valueOsake;
			String code = data[5];

			Menu menu = new Menu(name, price, meal, drink, osake, code);

			// リストの中にくっつけていく
			csvDataList.add(menu);

			// デバック用
			// System.out.print(menu.getName());
			// System.out.print(df.format(menu.getPrice()));
			// System.out.print(menu.getMeal());
			// System.out.print(menu.getDrink());
			// System.out.print(menu.getOsake());
			// System.out.println(menu.getCode());
			//
		}

		br.close();
		return csvDataList;
	}
	// -------------------ここまで--------------------

	// -------------------金額読み込み処理--------------------

	public static int readMoney() throws IOException {

		System.out.println("ガチャにいくらつぎ込みますか？");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();

		int money = Integer.parseInt(s);
		System.out.println(money + "円溶けました");

		return money;
	}

	// -------------------プルダウン実像--------------------

	public void pullDown() {

		List<Integer> money = new ArrayList<Integer>();

		money.add(500);
		money.add(1000);
		money.add(2000);
		money.add(3000);
		money.add(4000);
		money.add(5000);

		JComboBox combo = new JComboBox((ComboBoxModel) money);

		System.out.println("ガチャにいくらつぎ込みますか？");

		JPanel p = new JPanel();
		p.add((Component) money);
		getContentPane().add(p, BorderLayout.CENTER);

		// return 0;
	}

	// -------------------ここまで--------------------

	// -------------------ここまで--------------------

	// -------------------フラグ確認と仕分け処理--------------------
	public static List<Menu> osakeFlag(List<Menu> csvDataList) throws IOException {

		while (true) {
			System.out.println("お酒は飲まれますか？");
			System.out.println("飲みます→1を入力 飲みませぬ→2を入力");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String flag = br.readLine();

			if ((flag.equals("1")) | (flag.equals("１"))) {

				break;
			}
			if ((flag.equals("2")) | (flag.equals("２"))) {
				Iterator<Menu> iterator = csvDataList.iterator();

				while (iterator.hasNext()) {

					Menu m = iterator.next();

					if (m.getOsake() == 1) {
						iterator.remove();

					}

				}

				break;

			}
			if (flag.contains("ABBAAB")) {
				Iterator<Menu> iterator = csvDataList.iterator();

				while (iterator.hasNext()) {

					Menu m = iterator.next();

					if (m.getMeal() == 1) {
						iterator.remove();

					}

				}
				break;
			} else {
				System.out.println("1か2って書いてるやん…");
			}

		}
		return csvDataList;

	}
	// -------------------ここまで--------------------

	// -------------------ガチャを回す処理--------------------

	public static List<Menu> gacha(List<Menu> csvDataList, int money, List<Integer> wordCntList) {

		// めにゅーをがっしり減らしていくので別のリストに避難させる
		List<Menu> oshinagaki = new ArrayList<Menu>();
		List<Menu> mawaru = new ArrayList<Menu>();
		oshinagaki = csvDataList;
		wordCntList = wordCntMax(oshinagaki);

		// 合計金額を計算するための変数用意
		int goukeigaku;
		int gaku = money;
		DecimalFormat df = new DecimalFormat("#円");

		System.out.println("☆彡今回のメニューは！？☆彡");
		System.out.println("");

		System.out.println("-------------------------------------------------------------------------");
		System.out.printf("%-20s%30s%10s%n", "メニュー", "注文番号", "金額");
		System.out.println("-------------------------------------------------------------------------");

		// お金が尽きるまでガチャを回させる
		while (money > 90) {

			Iterator<Menu> iterator = oshinagaki.iterator();

			while (iterator.hasNext()) {

				Menu m = iterator.next();

				if (m.getPrice() > money) {
					iterator.remove();

				}

			}
			// 調整後のメニュー分の数をゲットする、そして回す
			int random = (int) (Math.random() * oshinagaki.size());
			Menu hinmoku = oshinagaki.get(random);

			mawaru.add(hinmoku);

			goukeigaku = hinmoku.getPrice() + gaku;

			// メニューの金額から支払う
			int okane = money - hinmoku.getPrice();
			money = okane;

		}

		// お品書き表示

		for (int i = 0; i < mawaru.size(); i++) {
			if ((i + 1) < 10) {
				System.out.print(" ");
			}
			System.out.print((i + 1) + ":");
			System.out.print(mawaru.get(i).getName());

			int space = wordCntList.get(0) - wordCnt(mawaru.get(i).getName());

			for (int j = 0; j < space + 1; j++) {
				System.out.print(" ");
			}

			System.out.print(",");
			// 金額は右詰めで表示
			space = wordCntList.get(2) - Integer.toString(mawaru.get(i).getPrice()).length();
			for (int j = 0; j < space + 1; j++) {
				System.out.print(" ");
			}
			System.out.print("," + mawaru.get(i).getCode());
			space = wordCntList.get(1) - wordCnt(mawaru.get(i).getCode());
			for (int j = 0; j < space + 1; j++) {
				System.out.print(" ");
			}

			System.out.print(df.format(mawaru.get(i).getPrice()) + "\n");

		}
		// System.out.print(hinmoku.getName() + " ");
		// System.out.print(df.format(hinmoku.getPrice())+ " ");
		// System.out.print("ご注文番号 : ");
		// System.out.printf("%-10s",hinmoku.getCode());
		// System.out.println("");

		goukeigaku = gaku - money;
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("お会計は" + goukeigaku + "円です！");

		return oshinagaki;
	}

	// -------------------ここまで--------------------

	// -------------------各要素の最大文字数をカウント--------------------
	public static List<Integer> wordCntMax(List<Menu> csvDataList) {

		int nameMax = 0;
		int priceMax = 0;
		int codeMax = 0;

		// wordCnt()で半角全角の対応をしつつ、文字数の最大値を探す

		for (int i = 0; i < csvDataList.size(); i++) {

			nameMax = Math.max(nameMax, wordCnt(csvDataList.get(i).getName()));
			priceMax = Math.max(priceMax, Integer.toString(csvDataList.get(i).getPrice()).length());
			codeMax = Math.max(codeMax, wordCnt(csvDataList.get(i).getCode()));

		}

		List<Integer> wordCntList = new ArrayList<Integer>();
		wordCntList.add(nameMax);
		wordCntList.add(priceMax);
		wordCntList.add(codeMax);

		return wordCntList;
	}

	// -------------------ここまで--------------------
	// -------------------最大文字数を格納--------------------

	public static int wordCnt(String c) {

		int cnt = 0;
		char[] word = c.toCharArray();

		for (int i = 0; i < word.length; i++) {

			if (String.valueOf(word[i]).getBytes().length <= 1) {

				cnt += 1;
			} else {
				cnt += 2;
			}

		}

		return cnt;
	}
	// -------------------ここまで--------------------

	// -------------------文字数カウント（半角全角の対応）--------------------
	public static int wordCnt1(String c) {

		int cnt = 0;
		char[] word = c.toCharArray();
		for (int i = 0; i < word.length; i++) {
			if (String.valueOf(word[i]).getBytes().length <= 1) {

				cnt += 1;
			} else {

				cnt += 2;
			}

		}
		return cnt;
	}

	// -------------------ここまで--------------------

}
