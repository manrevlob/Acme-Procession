package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Costume;

@Component
@Transactional
public class CostumeToStringConverter implements Converter<Costume, String> {

	@Override
	public String convert(Costume entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
