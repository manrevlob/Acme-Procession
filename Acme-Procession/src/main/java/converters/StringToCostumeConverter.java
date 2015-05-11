package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CostumeRepository;
import domain.Costume;

@Component
@Transactional
public class StringToCostumeConverter implements Converter<String, Costume> {

	@Autowired
	CostumeRepository costumeRepository;

	@Override
	public Costume convert(String text) {
		Costume result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = costumeRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}