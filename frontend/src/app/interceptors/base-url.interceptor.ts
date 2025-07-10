import { HttpInterceptorFn } from '@angular/common/http';
import { environment } from '../../environments/environment';

export const baseUrlInterceptor: HttpInterceptorFn = (req, next) => {
  const isRelative = !/^https?:\/\//i.test(req.url);

  const updatedReq = isRelative
    ? req.clone({ url: `${environment.apiUrl}${req.url}` })
    : req;

  return next(updatedReq);
};
