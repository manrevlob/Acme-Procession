package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Brother;

@Component
@Transactional
public class BrotherToStringConverter implements Converter<Brother, String> {

	@Override
	public String convert(Brother entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
