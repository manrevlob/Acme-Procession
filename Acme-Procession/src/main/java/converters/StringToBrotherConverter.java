package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BrotherRepository;
import domain.Brother;

@Component
@Transactional
public class StringToBrotherConverter implements Converter<String, Brother> {

	@Autowired
	BrotherRepository brotherRepository;

	@Override
	public Brother convert(String text) {
		Brother result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = brotherRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}