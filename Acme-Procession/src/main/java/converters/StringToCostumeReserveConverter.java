package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CostumeReserveRepository;
import domain.CostumeReserve;

@Component
@Transactional
public class StringToCostumeReserveConverter implements Converter<String, CostumeReserve> {

	@Autowired
	CostumeReserveRepository costumeRepository;

	@Override
	public CostumeReserve convert(String text) {
		CostumeReserve result;
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