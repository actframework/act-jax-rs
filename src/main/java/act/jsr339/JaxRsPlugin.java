package act.jsr339;

/*-
 * #%L
 * ACT JAX-RS
 * %%
 * Copyright (C) 2017 - 2018 ActFramework
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import act.app.ActionContext;
import act.plugin.ControllerPlugin;
import org.osgl.$;
import org.osgl.Osgl;
import org.osgl.exception.NotAppliedException;
import org.osgl.http.H;
import org.osgl.http.H.Method;
import org.osgl.mvc.result.Result;
import org.osgl.util.C;

import java.lang.annotation.Annotation;
import java.util.Map;
import javax.ws.rs.*;

public class JaxRsPlugin extends ControllerPlugin {



    @Override
    protected boolean noDefaultPath() {
        return true;
    }

    @Override
    protected Map<Class<? extends Annotation>, Method> annotationMethodLookup() {
        return C.map(
                GET.class, Method.GET,
                POST.class, Method.POST,
                PUT.class, Method.PUT,
                DELETE.class, Method.DELETE,
                OPTIONS.class, Method.OPTIONS,
                PATCH.class, Method.PATCH
        );
    }

    @Override
    public Osgl.Function<ActionContext, Result> beforeHandler(Class<?> controllerClass, java.lang.reflect.Method actionMethod) {
        final Produces produces = actionMethod.getAnnotation(Produces.class);
        if (null == produces) {
            return super.beforeHandler(controllerClass, actionMethod);
        }
        final String contentType = produces.value()[0];
        if ("*/*".equals(contentType)) {
            return super.beforeHandler(controllerClass, actionMethod);
        }
        return new $.F1<ActionContext, Result>() {
            @Override
            public Result apply(ActionContext context) throws NotAppliedException, Osgl.Break {
                context.accept(H.Format.resolve(contentType));
                return null;
            }
        };
    }

    @Override
    protected PathAnnotationSpec urlContextAnnotation() {
        return new PathAnnotationSpec(Path.class, false, false);
    }
}
