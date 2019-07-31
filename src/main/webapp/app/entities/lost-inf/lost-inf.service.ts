import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILostInf } from 'app/shared/model/lost-inf.model';

type EntityResponseType = HttpResponse<ILostInf>;
type EntityArrayResponseType = HttpResponse<ILostInf[]>;

@Injectable({ providedIn: 'root' })
export class LostInfService {
    public resourceUrl = SERVER_API_URL + 'api/lost-infs';

    constructor(protected http: HttpClient) {}

    create(lostInf: ILostInf): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lostInf);
        return this.http
            .post<ILostInf>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(lostInf: ILostInf): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lostInf);
        return this.http
            .put<ILostInf>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILostInf>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILostInf[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(lostInf: ILostInf): ILostInf {
        const copy: ILostInf = Object.assign({}, lostInf, {
            lostime: lostInf.lostime != null && lostInf.lostime.isValid() ? lostInf.lostime.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.lostime = res.body.lostime != null ? moment(res.body.lostime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((lostInf: ILostInf) => {
                lostInf.lostime = lostInf.lostime != null ? moment(lostInf.lostime) : null;
            });
        }
        return res;
    }
}
