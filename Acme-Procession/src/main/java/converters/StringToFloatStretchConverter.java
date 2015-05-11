package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.FloatStretchRepository;
import domain.FloatStretch;

@Component
@Transactional
public class StringToFloatStretchConverter implements Converter<String, FloatStretch> {

	@Autowired
	FloatStretchRepository floatStretchRepository;

	@Override
	public FloatStretch convert(String text) {
		Float result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = floatStretchRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}