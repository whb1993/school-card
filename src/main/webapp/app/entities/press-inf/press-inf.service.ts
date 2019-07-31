import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPressInf } from 'app/shared/model/press-inf.model';

type EntityResponseType = HttpResponse<IPressInf>;
type EntityArrayResponseType = HttpResponse<IPressInf[]>;

@Injectable({ providedIn: 'root' })
export class PressInfService {
    public resourceUrl = SERVER_API_URL + 'api/press-infs';

    constructor(protected http: HttpClient) {}

    create(pressInf: IPressInf): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pressInf);
        return this.http
            .post<IPressInf>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pressInf: IPressInf): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pressInf);
        return this.http
            .put<IPressInf>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPressInf>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPressInf[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(pressInf: IPressInf): IPressInf {
        const copy: IPressInf = Object.assign({}, pressInf, {
            ptime: pressInf.ptime != null && pressInf.ptime.isValid() ? pressInf.ptime.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.ptime = res.body.ptime != null ? moment(res.body.ptime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((pressInf: IPressInf) => {
                pressInf.ptime = pressInf.ptime != null ? moment(pressInf.ptime) : null;
            });
        }
        return res;
    }
}
