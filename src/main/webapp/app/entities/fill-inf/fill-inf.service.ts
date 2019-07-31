import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFillInf } from 'app/shared/model/fill-inf.model';

type EntityResponseType = HttpResponse<IFillInf>;
type EntityArrayResponseType = HttpResponse<IFillInf[]>;

@Injectable({ providedIn: 'root' })
export class FillInfService {
    public resourceUrl = SERVER_API_URL + 'api/fill-infs';

    constructor(protected http: HttpClient) {}

    create(fillInf: IFillInf): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(fillInf);
        return this.http
            .post<IFillInf>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(fillInf: IFillInf): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(fillInf);
        return this.http
            .put<IFillInf>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFillInf>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFillInf[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(fillInf: IFillInf): IFillInf {
        const copy: IFillInf = Object.assign({}, fillInf, {
            filltime: fillInf.filltime != null && fillInf.filltime.isValid() ? fillInf.filltime.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.filltime = res.body.filltime != null ? moment(res.body.filltime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((fillInf: IFillInf) => {
                fillInf.filltime = fillInf.filltime != null ? moment(fillInf.filltime) : null;
            });
        }
        return res;
    }
}
