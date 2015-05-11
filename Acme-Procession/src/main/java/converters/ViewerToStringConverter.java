package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Viewer;

@Component
@Transactional
public class ViewerToStringConverter implements Converter<Viewer, String> {

	@Override
	public String convert(Viewer entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
