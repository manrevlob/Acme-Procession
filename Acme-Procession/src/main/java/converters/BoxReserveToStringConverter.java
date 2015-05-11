package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.BoxReserve;

@Component
@Transactional
public class BoxReserveToStringConverter implements Converter<BoxReserve, String> {

	@Override
	public String convert(BoxReserve entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
