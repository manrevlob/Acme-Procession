package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BoxInstanceRepository;
import domain.BoxInstance;

@Component
@Transactional
public class StringToBoxInstanceConverter implements Converter<String, BoxInstance> {

	@Autowired
	BoxInstanceRepository boxInstanceRepository;

	@Override
	public BoxInstance convert(String text) {
		BoxInstance result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = boxInstanceRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}