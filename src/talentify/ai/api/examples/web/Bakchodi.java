package talentify.ai.api.examples.web;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Bakchodi {

	public String getRandomWitty() {
		String movieOneLiner = (String) getMovieOneLiner();
		return movieOneLiner;
	}

	private Object getMovieOneLiner() {
		Object response = null;
		Set<String> movieOneLiners = getMovieOneLiners();
		int size = movieOneLiners.size();
		int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
		int i = 0;
		for (Object obj : movieOneLiners) {
			if (i == item)
				response = obj;
			i++;
		}
		return response;
	}

	private Set<String> getMovieOneLiners() {
		String source = "http://www.filmsite.org/funniestquotes.html";
		Set<String> oneLiners = new HashSet<>();
		oneLiners.add("Gentlemen, you can't fight in here! This is the War Room!");
		oneLiners.add(
				"I remember when I was a little boy, I-I once stole a pornographic book that was printed in Braille, and I used to rub the dirty parts.");
		oneLiners.add(
				"Just when I think you couldn't possibly be any dumber, you go and do somethin' like this -- and totally redeem yourself! Ha Ha!");
		oneLiners.add("God gave men brains larger than dogs' so they wouldn't hump women's legs at cocktail parties.");
		oneLiners.add("Could you repeat that, sir?");
		oneLiners.add("Mike. I'm tellin' ya, you're money. You're so f--kin' money!");
		oneLiners.add("Fat, drunk, and stupid is no way to go through life, son.");
		oneLiners.add("Nobody calls me Lebowski. You got the wrong guy. I'm the Dude, man.");
		return oneLiners;
	}

}
