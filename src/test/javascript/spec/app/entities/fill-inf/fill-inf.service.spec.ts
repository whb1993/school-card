/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FillInfService } from 'app/entities/fill-inf/fill-inf.service';
import { IFillInf, FillInf } from 'app/shared/model/fill-inf.model';

describe('Service Tests', () => {
    describe('FillInf Service', () => {
        let injector: TestBed;
        let service: FillInfService;
        let httpMock: HttpTestingController;
        let elemDefault: IFillInf;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(FillInfService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new FillInf(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        filltime: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a FillInf', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        filltime: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        filltime: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new FillInf(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a FillInf', async () => {
                const returnedFromService = Object.assign(
                    {
                        cardno: 'BBBBBB',
                        cardstyle: 'BBBBBB',
                        fillmoney: 'BBBBBB',
                        filltime: currentDate.format(DATE_TIME_FORMAT),
                        adId: 'BBBBBB',
                        fillnum: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        filltime: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of FillInf', async () => {
                const returnedFromService = Object.assign(
                    {
                        cardno: 'BBBBBB',
                        cardstyle: 'BBBBBB',
                        fillmoney: 'BBBBBB',
                        filltime: currentDate.format(DATE_TIME_FORMAT),
                        adId: 'BBBBBB',
                        fillnum: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        filltime: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a FillInf', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
