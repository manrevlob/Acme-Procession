package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BoxReserveRepository;
import domain.BoxReserve;

@Component
@Transactional
public class StringToBoxReserveConverter implements Converter<String, BoxReserve> {

	@Autowired
	BoxReserveRepository boxReserveRepository;

	@Override
	public BoxReserve convert(String text) {
		BoxReserve result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = boxReserveRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}