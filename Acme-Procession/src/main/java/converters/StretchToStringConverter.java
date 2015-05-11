package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Stretch;

@Component
@Transactional
public class StretchToStringConverter implements Converter<Stretch, String> {

	@Override
	public String convert(Stretch entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
