package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.StretchOrder;

@Component
@Transactional
public class StretchOrderToStringConverter implements Converter<StretchOrder, String> {

	@Override
	public String convert(StretchOrder entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
