package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CarvingRepository;
import domain.Carving;

@Component
@Transactional
public class StringToCarvingConverter implements Converter<String, Carving> {

	@Autowired
	CarvingRepository carvingRepository;

	@Override
	public Carving convert(String text) {
		Carving result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = carvingRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}