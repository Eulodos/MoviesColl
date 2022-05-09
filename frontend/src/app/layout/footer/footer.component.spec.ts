import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { FooterComponent } from './footer.component';


describe('FooterComponent', () => {
  let component: FooterComponent;
  let fixture: ComponentFixture<FooterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FooterComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a link to the author\'s repository', () => {
    const links = fixture.debugElement.queryAll(By.css('a'));
    expect(links.length).toBe(1);
    expect(links[0].nativeElement.href).toContain('Eulodos');
  })

  it('should have the copyright symbol', () => {
    const paragraph = fixture.debugElement.query(By.css('p')).nativeElement;
    expect(paragraph.textContent).toContain('©');
  })
});
