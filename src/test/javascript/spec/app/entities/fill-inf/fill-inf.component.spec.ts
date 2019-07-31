/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SchoolCardTestModule } from '../../../test.module';
import { FillInfComponent } from 'app/entities/fill-inf/fill-inf.component';
import { FillInfService } from 'app/entities/fill-inf/fill-inf.service';
import { FillInf } from 'app/shared/model/fill-inf.model';

describe('Component Tests', () => {
    describe('FillInf Management Component', () => {
        let comp: FillInfComponent;
        let fixture: ComponentFixture<FillInfComponent>;
        let service: FillInfService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SchoolCardTestModule],
                declarations: [FillInfComponent],
                providers: []
            })
                .overrideTemplate(FillInfComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FillInfComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FillInfService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FillInf(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.fillInfs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
