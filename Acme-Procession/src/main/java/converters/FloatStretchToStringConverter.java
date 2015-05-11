package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.FloatStretch;

@Component
@Transactional
public class FloatStretchToStringConverter implements Converter<FloatStretch, String> {

	@Override
	public String convert(FloatStretch entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
