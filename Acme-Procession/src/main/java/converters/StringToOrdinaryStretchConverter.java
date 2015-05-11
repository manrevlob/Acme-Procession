package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OrdinaryStretchRepository;
import domain.OrdinaryStretch;

@Component
@Transactional
public class StringToOrdinaryStretchConverter implements Converter<String, OrdinaryStretch> {

	@Autowired
	OrdinaryStretchRepository ordinaryStretchRepository;

	@Override
	public OrdinaryStretch convert(String text) {
		OrdinaryStretch result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = ordinaryStretchRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}