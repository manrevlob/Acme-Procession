package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.OrdinaryStretch;

@Component
@Transactional
public class OrdinaryStretchToStringConverter implements Converter<OrdinaryStretch, String> {

	@Override
	public String convert(OrdinaryStretch entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
