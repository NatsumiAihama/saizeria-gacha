package saizeria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {

		List<Menu> csvDataList = new ArrayList<Menu>();
		List<Integer> wordCntList = new ArrayList<Integer>();
		int money = 0;

		while (true) {
			System.out.println("★サイゼリヤガチャ★");
			System.out.println("☆彡グランドメニュー限定☆彡");

			// メニュー読み込み
			csvDataList = Syori.readMenu();

			// 金額設定
			money = Syori.readMoney();

			// 飲酒確認
			Syori.osakeFlag(csvDataList);
			System.out.println("スタート！");

			// ガチャを引く
			Syori.gacha(csvDataList, money, wordCntList);

			System.out.println("もう一発やる？");
			System.out.println("");
			System.out.print("Yes!Yes!Yes! →1押してね♡  NOOOOOOOOO!! →2押してね♡");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String again = br.readLine();

			if (again.equals("1")) {
				continue;
			}
			if (again.equals("2")) {
				System.out.println("またきてね！");
				break;
			} else {
				System.out.println("1か2押せって書いたじゃん…");

			}
		}
		// -----------------------------------------------------------------------

	}

}
