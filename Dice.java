import java.util.*;

public class Dice {
	
	int score;
	int dice;
	
	public Dice () {
		dice = 6;
		score = 0;
	}
	
	public ArrayList<Integer> roll(int amount) {
		ArrayList<Integer> hand = new ArrayList<Integer>();
		for (int i = 0; i < amount; i++) {
			hand.add((int)(1 + (6 * Math.random())));
		}
		return hand;		
	}
	
	public int count(ArrayList<Integer> hand, int checkfor) {
		int total = 0;
		for (int i : hand) {
			if (i == checkfor)
				total++;
		}
		return total;
	}
	
	public int checktriplesplus(ArrayList<Integer> hand) {
		int addscore = 0;
		for (int i = 2; i <= dice; i++) {
			if (count(hand, i) >= 3) 
				dice -= count(hand, i);
			if (count(hand, i) == 3)
				addscore += (100 * i);
			else if (count(hand, i) == 4)
				addscore += (100 * 2 * i);
			else if (count(hand, i) == 5)
				addscore += (100 * 3 * i);
			else if (count(hand, i) == 6)
				addscore += (100 * 4 * i);	
		}
		return addscore;
	}
	
	public int checkonesfives(ArrayList<Integer> hand) {
		int addscore = 0;
		if (count(hand, 5) < 3) {
			addscore += (50 * count(hand, 5));
			dice -= count(hand, 5);
		}
		addscore += (100 * count(hand, 1));
		dice -= count(hand, 1);
		return addscore;	
	}
	
	public double calcexpreturn(int s, int d) {
		double r = 0;
		if (d == 6)
			r = s * 0.879;
		if (d == 5)
			r = s * 0.845;
		if (d == 4)
			r = s * 0.789;
		if (d == 3)
			r = s * 0.699;
		if (d == 2)
			r = s * 0.555;
		if (d == 1)
			r = s * 0.333;
		return r;
	}
	
	public String paste(ArrayList<Integer> hand) {
		String s = "";
		for (int i : hand) {
			s = s + i;
		}
		return s;
	}
	
	public int playgame(int minexpectedreturn) {
		Dice d = new Dice();
		
		Scanner input = new Scanner(System.in);
		
		int diceleft = 6;
		int oscore = 0;
		int finalscore = 0;
		ArrayList<Integer> hand = new ArrayList<Integer>();
		
		boolean alive = true;
		while (alive == true) {
			hand = d.roll(diceleft);
			
			oscore = d.score;
			d.score += (d.checktriplesplus(hand) + d.checkonesfives(hand));
			
			diceleft = d.dice;
			
			if (d.score == oscore) {
				finalscore = 0;
				alive = false;
			} else {
				if (diceleft == 0) {
					diceleft = 6;
					d.dice = 6;
				}
				if (calcexpreturn(d.score, diceleft) < minexpectedreturn) {
					finalscore = d.score;
					alive = false;
				} 
			}				
		}			
		return finalscore;
	}
		
	public static void main (String [] args) {
		Dice d = new Dice();
		double total = 0;
		int games = 1000000;
		ArrayList<Integer> hand = new ArrayList<Integer>();
		
		for (int j = 10; j <= 1000; j+= 10) {
			for (int i = 0; i < games; i++) {
				total += d.playgame(j);
			}	
			System.out.println("Average score with MER " + j + ": " + (total / games));
			total = 0;
		}
	}
}

