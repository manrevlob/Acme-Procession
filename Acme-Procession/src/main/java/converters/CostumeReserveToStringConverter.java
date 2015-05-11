package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CostumeReserve;

@Component
@Transactional
public class CostumeReserveToStringConverter implements Converter<CostumeReserve, String> {

	@Override
	public String convert(CostumeReserve entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
