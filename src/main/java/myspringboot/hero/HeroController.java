package myspringboot.hero;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http:localhost:4200")
@RestController
@RequestMapping(value = "/heros")
public class HeroController {

	private List<Hero> heros = new ArrayList<>();	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	HeroController() {
		buildHeros();
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Hero> getHeros() {
		logger.debug("목록조회");
		return this.heros;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Hero getHero(@PathVariable("id") Long id) {
		logger.debug("상세조회 : "+id);
		return this.heros.stream().filter
				(hero -> hero.getId() == id).findFirst().orElse(null);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Hero saveHero(@RequestBody Hero hero) {
		logger.debug("등록 전: "+hero);
		Long nextId = 0L;
		if (this.heros.size() != 0) {
			Hero lastHero = this.heros.stream().skip(this.heros.size() - 1).findFirst().orElse(null);
			nextId = lastHero.getId() + 1;
		}
		//전체 목록의 마지막을 불러와서 id를 생성하는 process

		hero.setId(nextId);
		this.heros.add(hero);
		logger.debug("등록 후: "+hero);
		return hero;

	}

	@RequestMapping(method = RequestMethod.PUT)
	public Hero updateHero(@RequestBody Hero hero) {
		logger.debug("업데이트 전 : "+hero);
		Hero modifiedHero = this.heros.stream().filter(u -> u.getId() == hero.getId()).findFirst().orElse(null);
		modifiedHero.setName(hero.getName());
		logger.debug("업데이트 후 : "+modifiedHero);
		return modifiedHero;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean deleteHero(@PathVariable Long id) {
		logger.debug("삭제 : " + id);
		Hero deleteHero = this.heros.stream().filter(h -> h.getId() == id).findFirst().orElse(null);
		if (deleteHero != null) {
			logger.debug("삭제할 놈 : " + deleteHero);
			this.heros.remove(deleteHero);
			return true;
		} else  {
			return false;
		}
	}
	//검색
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	public List<Hero> searchHeroes(@PathVariable String name) {
		return this.heros.stream()
			.filter(hero -> hero.getName().contains(name))
			.collect(Collectors.toList());
	}
	
	
	void buildHeros() {
		Hero hero1 = new Hero(11L,"진영이형 잘하긴 잘해");
		Hero hero2 = new Hero(12L,"김민식");
		Hero hero3 = new Hero(13L,"송장원");
		Hero hero4 = new Hero(14L,"Celeritas");
		Hero hero5 = new Hero(15L,"Magneta");
		Hero hero6 = new Hero(16L,"RubberMan");
		Hero hero7 = new Hero(16L,"Dynama");
		Hero hero8 = new Hero(18L,"Dr IQ");
		Hero hero9 = new Hero(19L,"Magma");
		Hero hero10 = new Hero(20L,"Tornado");

		heros.add(hero1);
		heros.add(hero2);
		heros.add(hero3);
		heros.add(hero4);
		heros.add(hero5);
		heros.add(hero6);
		heros.add(hero7);
		heros.add(hero8);
		heros.add(hero9);
		heros.add(hero10);
		
	}
}


