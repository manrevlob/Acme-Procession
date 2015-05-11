package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Carving;

@Component
@Transactional
public class CarvingToStringConverter implements Converter<Carving, String> {

	@Override
	public String convert(Carving entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
