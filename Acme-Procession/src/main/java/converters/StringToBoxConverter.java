package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BoxRepository;
import domain.Box;

@Component
@Transactional
public class StringToBoxConverter implements Converter<String, Box> {

	@Autowired
	BoxRepository boxRepository;

	@Override
	public Box convert(String text) {
		Box result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = boxRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}