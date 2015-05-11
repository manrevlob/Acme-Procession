package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.StretchRepository;
import domain.Stretch;

@Component
@Transactional
public class StringToStretchConverter implements Converter<String, Stretch> {

	@Autowired
	StretchRepository stretchRepository;

	@Override
	public Stretch convert(String text) {
		Stretch result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = stretchRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}